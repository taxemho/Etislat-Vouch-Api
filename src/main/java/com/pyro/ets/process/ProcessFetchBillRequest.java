package com.pyro.ets.process;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pyro.bean.FetchBillRespJson;
import com.pyro.bean.FetchbillReqBeanJson;
import com.pyro.bean.Root;
import com.pyro.utils.DbOperations;
import com.pyro.utils.SendRequest;

@Service
public class ProcessFetchBillRequest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SendRequest sendRequest;

    @Autowired
    private DbOperations dbOperations;

    @Value("${ethisalt.url}")
    private String ethisaltUrl;

    @Value("${etisalat.fetch.url}")
    private String etisalatFetchUrl;

    @Value("${extra.charges}")
    private boolean extraCharges;

    @Value("${extra.charges.amount}")
    private String extraChargesAmount;

    public String processFetchBill(FetchbillReqBeanJson req) {
        Root replay = new Root();
        FetchBillRespJson fresp = new FetchBillRespJson();
        JSONObject json;
        String resp = "";
        Gson gson = new Gson();
        String finalreq = "";
        String fetchreq = "<BalanceInquiry ServiceType=\"@#servicetype@#\" SchemaVersion=\"2.0\">"
                + "<NationalNo>@#nationalno@#</NationalNo>"
                + "<CustomerID>00000001</CustomerID>"
                + "<ChannelID>IVR123456789012</ChannelID>"
                + "<BranchLocation>Jamal</BranchLocation>"
                + "<BankID>3456</BankID>"
                + "<SenderID>00000068</SenderID>"
                + "</BalanceInquiry>";
        try {
            finalreq = fetchreq.replace("@#servicetype@#", req.getServiceType())
                               .replace("@#nationalno@#", req.getNationalno());

            resp = sendRequest.CallWebservice(ethisaltUrl, finalreq);
            json = XML.toJSONObject(resp);

            replay = gson.fromJson(json.toString(), Root.class);
            System.out.println(replay);
            
            // Populate fresp object
            populateFetchBillResponse(fresp, replay, req);

            return gson.toJson(fresp);
        } catch (Exception e) {
            e.printStackTrace();
            fresp.setErrorCode("5000");
            fresp.setErrorMessage("Technical Failure Contact to support!");
        }
        return gson.toJson(fresp);
    }

    public String fetchBill(FetchbillReqBeanJson req) {
        String finalreq = "";
        Root replay = new Root();
        FetchBillRespJson fresp = new FetchBillRespJson();
        JSONObject json;
        String resp = "", res;

        Gson gson = new Gson();
        try {
            finalreq = etisalatFetchUrl.replace("@#nationalno@#", req.getNationalno())
                                       .replace("@#servicetype@#", req.getServiceType())
                                       .replace("@#stype@#", req.getServiceType())
                                       .replace("@#msisdn@#", req.getMsisdn());

            resp = sendRequest.sendGET(finalreq);
            if (resp.contains("<TransactionID>")) {
                resp = resp.replace("<TransactionID>", "<TransactionID><![CDATA[")
                           .replace("</TransactionID>", "]]></TransactionID>");
            }
            System.out.println("fetch resp = " + resp);
            json = XML.toJSONObject(resp);

            replay = gson.fromJson(json.toString(), Root.class);
            System.out.println(replay);
            
            // Populate fresp object
            populateFetchBillResponse(fresp, replay, req);

            if (fresp.getErrorCode().equals("0") || fresp.getErrorCode().equals("00")) {
                handleSuccessfulFetch(fresp, req);
            } else {
                fresp.setResponseCode(fresp.getErrorCode());
                fresp.setResponseMessage(fresp.getErrorMessage());
            }
            return gson.toJson(fresp);
        } catch (Exception e) {
            e.printStackTrace();
            fresp.setErrorCode("5000");
            fresp.setErrorMessage("Technical Failure Contact to support!");
            fresp.setResponseCode("5000");
            fresp.setResponseMessage("Technical Failure Contact to support!");
        }
        return gson.toJson(fresp);
    }

    private void populateFetchBillResponse(FetchBillRespJson fresp, Root replay, FetchbillReqBeanJson req) {
        fresp.setAmountDue(replay.getReply().getAmountDue() + "" == null ? "0" : replay.getReply().getAmountDue() + "");
        fresp.setErrorCode(replay.getReply().getErrorCode() == null ? "" : replay.getReply().getErrorCode());
        fresp.setErrorMessage(replay.getReply().getErrorMessage() == null ? "" : replay.getReply().getErrorMessage());
        fresp.setNationalNo(req.getNationalno());
        fresp.setServiceType(req.getServiceType());
        fresp.setReplyDateStamp(replay.getReply().getReplyDateStamp() == null ? "" : replay.getReply().getReplyDateStamp());
        fresp.setTransactionDateStamp(replay.getReply().getTransactionDateStamp() == null ? "" : replay.getReply().getTransactionDateStamp());
        fresp.setTransactionID(replay.getReply().getTransactionID() == null ? "" : replay.getReply().getTransactionID());
    }

    private void handleSuccessfulFetch(FetchBillRespJson fresp, FetchbillReqBeanJson req) {
        if (extraCharges && Double.parseDouble(fresp.getAmountDue()) > 0.0) {
            fresp.setService_charges(extraChargesAmount);
        }

        fresp.setResponseCode("000");
        fresp.setResponseMessage("Fetch Successful");
        String savequery = "insert into tr_table_etisalat_fetch(transactionid,service_type,replaydatestamp,transdatestamp,amountdue,inti_time,nationalno)"
                + " values('" + fresp.getTransactionID() + "','" + req.getServiceType() + "','"
                + fresp.getReplyDateStamp() + "','" + fresp.getTransactionDateStamp() + "','"
                + fresp.getAmountDue() + "',sysdate,'" + req.getNationalno() + "')";
        String res = dbOperations.insertTransaction(savequery, dataSource);
        System.out.println("Fetch save response = " + res);
    }
}
