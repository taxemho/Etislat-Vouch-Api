package com.pyro.ets.process;

import javax.sql.DataSource;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.corereqbeans.BillPayRequestForAll;
import com.corereqbeans.BillPayResponseForAll;
import com.corereqbeans.Corerequest;
import com.pyro.bean.CallBackResponseData;
import com.pyro.utils.ServiceWalletDeduction;

@Service
public class ProcessAsyncReq {
    private static final Logger log = LoggerFactory.getLogger(ProcessAsyncReq.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ServiceWalletDeduction serviceWalletDeduction;

    @Value("${hub.callback.url}")
    private String hubCallbackUrl;

    public void sendAsyncResponse(BillPayRequestForAll req, BillPayResponseForAll resp) {
        Corerequest corereq = (Corerequest) req.getObj();
        CallBackResponseData cbresp = new CallBackResponseData();
        try {
            if (resp != null && resp.getResult() != null) {
                if (resp.getResult().equalsIgnoreCase("PYRO1000")) {
                    cbresp = setresponse(req, resp);
                } else if (resp.getResult().equalsIgnoreCase("PYRO2000")) {
                    cbresp = setresponse(req, resp);
                    corereq.setWalletoperationtype("1");
                    // Uncomment the following line if needed
                    // serviceWalletDeduction.updateServiceWallet(corereq, dataSource);
                } else {
                    cbresp = setresponse(req, resp);
                }
            }
            sendResponsetoHub(cbresp);
        } catch (Exception e) {
            log.error("Error in sendAsyncResponse", e);
        }
    }

    public void sendResponsetoHub(CallBackResponseData resp) {
        try {
            WebClient wc = WebClient.create(hubCallbackUrl);
            log.info("Callback URL : {} | {}", hubCallbackUrl, resp.toString());

            WebClient.getConfig(wc).getHttpConduit().getClient().setConnectionTimeout(200000L);
            WebClient.getConfig(wc).getHttpConduit().getClient().setReceiveTimeout(200000L);
            wc.post(resp, Void.class);
        } catch (Exception e) {
            log.error("Error in sendResponsetoHub", e);
        }
    }

    public CallBackResponseData setresponse(BillPayRequestForAll req, BillPayResponseForAll res) {
        CallBackResponseData response = new CallBackResponseData();
        try {
            String status = "100";
            if (res.getResult() != null) {
                if (res.getResult().equalsIgnoreCase("PYRO1000")) {
                    status = "0";
                } else if (res.getResult().equalsIgnoreCase("PYRO2000")) {
                    status = "1";
                }
            }
            Corerequest corereq = (Corerequest) req.getObj();
            String opinfo = (res.getOperatorrefno() != null) ? res.getOperatorrefno() : "---";

            response.setAmount(req.getAmount());
            response.setObject(res.getObj());
            response.setDescription(res.getDescription());
            response.setDestmsisdn(req.getSubscriberno());
            response.setOperatorid(opinfo);
            response.setSrcmsisdn("");
            response.setStatus(status);
            response.setTransactionId(req.getTransId());
            response.setOptional1(req.getOperator());
            response.setOptional2(corereq.getSpid());
        } catch (Exception e) {
            log.error("Error in setresponse", e);
        }
        return response;
    }
}
