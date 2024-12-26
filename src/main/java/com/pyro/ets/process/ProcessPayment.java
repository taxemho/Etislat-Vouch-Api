package com.pyro.ets.process;

import com.google.gson.Gson;
import com.pyro.bean.BillPayRequestForAll;
import com.pyro.bean.BillPayResponseForAll;
import com.pyro.bean.Corerequest;
import com.pyro.bean.Root;
import com.pyro.bean.SaveDataDetailsBean;
import com.pyro.utils.DbOperations;
import com.pyro.utils.SendRequest;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;

@Service
public class ProcessPayment {
    private static final Logger log = LoggerFactory.getLogger(ProcessPayment.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DbOperations dbOperations;

    @Autowired
    private SendRequest sendRequest;

    @Autowired
    private ProcessAsyncReq processAsyncReq;

    @Value("${etisalat.pay.url}")
    private String etisalatPayUrl;

    @Value("${extra.charges}")
    private boolean extraCharges;

    @Value("${extra.charges.amount}")
    private String extraChargesAmount;

    public BillPayResponseForAll processPayment(BillPayRequestForAll req) {
        BillPayResponseForAll resp = new BillPayResponseForAll();
        Corerequest corereq = (Corerequest) req.getObj();
        SaveDataDetailsBean savedata = new SaveDataDetailsBean();
        String paymentreq = "", finalreq = "", res = "";
        Root replay = new Root();
        JSONObject json;
        Gson gson = new Gson();

        try {
            if (!validationRequest(req)) {
                resp.setResult("PYRO1001");
                resp.setDescription("Mandatory Parameter Missing");
                return resp;
            }

            savedata = dbOperations.getSavedData(req.getTransId(), dataSource);
            if (savedata == null || savedata.getTransactionid() == null) {
                resp.setResult("PYRO1002");
                resp.setDescription("Transaction Not Found");
                return resp;
            }

            paymentreq = "<Payment ServiceType=\"@#servicetype@#\" SchemaVersion=\"2.0\">"
                    + "<NationalNo>@#nationalno@#</NationalNo>"
                    + "<CustomerID>00000001</CustomerID>"
                    + "<ChannelID>IVR123456789012</ChannelID>"
                    + "<BranchLocation>Jamal</BranchLocation>"
                    + "<BankID>3456</BankID>"
                    + "<SenderID>00000068</SenderID>"
                    + "<Amount>@#amount@#</Amount>"
                    + "<TransactionID>@#transactionid@#</TransactionID>"
                    + "</Payment>";

            finalreq = paymentreq.replace("@#servicetype@#", savedata.getServicetype())
                    .replace("@#nationalno@#", savedata.getNationalno())
                    .replace("@#amount@#", savedata.getAmountdue())
                    .replace("@#transactionid@#", savedata.getTransactionid());

            log.info("Payment Request: {}", finalreq);

            String paymentResponse = sendRequest.CallWebservice(etisalatPayUrl, finalreq);
            log.info("Payment Response: {}", paymentResponse);

            if (paymentResponse.contains("<TransactionID>")) {
                paymentResponse = paymentResponse.replace("<TransactionID>", "<TransactionID><![CDATA[")
                        .replace("</TransactionID>", "]]></TransactionID>");
            }

            json = XML.toJSONObject(paymentResponse);
            replay = gson.fromJson(json.toString(), Root.class);

            resp.setResult(replay.getReply().getErrorCode());
            resp.setDescription(replay.getReply().getErrorMessage());
            resp.setOperatorrefno(replay.getReply().getTransactionID());
            resp.setObj(replay);

            if (resp.getResult().equals("0") || resp.getResult().equals("00")) {
                handleSuccessfulPayment(req, resp, corereq, savedata);
            } else {
                handleFailedPayment(req, resp, corereq);
            }

        } catch (Exception e) {
            log.error("Error in processPayment", e);
            resp.setResult("PYRO1000");
            resp.setDescription("Technical Failure");
        }

        processAsyncReq.sendAsyncResponse(req, resp);
        return resp;
    }

    private boolean validationRequest(BillPayRequestForAll req) {
        return req != null && req.getTransId() != null && !req.getTransId().isEmpty()
                && req.getServicekey() != null && !req.getServicekey().isEmpty()
                && req.getAmount() != null && !req.getAmount().isEmpty();
    }

    private void handleSuccessfulPayment(BillPayRequestForAll req, BillPayResponseForAll resp, Corerequest corereq, SaveDataDetailsBean savedata) {
        resp.setResult("PYRO1000");
        resp.setDescription("Transaction Successful");

        String updateQuery = "update tr_table_etisalat_fetch set status='SUCCESS',update_time=sysdate where transactionid='"
                + savedata.getTransactionid() + "'";
        dbOperations.insertTransaction(updateQuery, dataSource);

        if (extraCharges && Double.parseDouble(savedata.getAmountdue()) > 0.0) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("CHARGES", extraChargesAmount);
            corereq.setAdditionalProperties(hm);
        }
    }

    private void handleFailedPayment(BillPayRequestForAll req, BillPayResponseForAll resp, Corerequest corereq) {
        resp.setResult("PYRO2000");
        resp.setDescription("Transaction Failed");
        corereq.setWalletoperationtype("1");
    }
}
