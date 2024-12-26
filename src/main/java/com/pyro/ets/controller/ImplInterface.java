package com.pyro.ets.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corereqbeans.BillPayRequestForAll;
import com.pyro.bean.FetchbillReqBeanJson;
import com.pyro.bean.SavebillReqBean;
import com.pyro.degi.DeSignedData;
import com.pyro.process.ProcessFetchBillRequest;
import com.pyro.process.ProcessPayment;
import com.pyro.utils.Config;
import com.pyro.utils.DbOperations;
import com.pyro.utils.LogRequester;
import com.pyro.utils.SendMail;
import com.pyro.utils.Utils;

@Service
public class ImplInterface implements InterfaceController {

    @Autowired
    private DataSource ds;

    @Autowired
    private DataSource ds1;

    @Autowired
    private ProcessFetchBillRequest processFetchBillRequest;

    @Autowired
    private DbOperations dbOperations;

    @Autowired
    private ProcessPayment processPayment;

    @Autowired
    private SendMail sendMail;

    private final LogRequester log;

    public ImplInterface() {
        this.log = new LogRequester();
    }

    @Override
    public String getFetchBill(String req) {
        FetchbillReqBeanJson reqBean;
        String resp = "";
        try {
            reqBean = Utils.fromJson(req, FetchbillReqBeanJson.class);
            Config.debuglog.writeLog(getClass().getName() + "|| getFetchBill request || " + reqBean);
            if (isInvalidFetchBillRequest(reqBean)) {
                resp = "{\"responseCode\":\"5001\",\"status\":\"FAILED\",\"responseMessage\":\"Mandatory Parameter Missing\"}";
            } else {
                System.out.println(" = Processing Request = ");
                resp = processFetchBillRequest.fetchBill(reqBean);
            }
        } catch (Exception e) {
            resp = "{\"responseCode\":\"5000\",\"status\":\"FAILED\",\"responseMessage\":\"Internal Server Error\"}";
            Config.debuglog.writeLog(getClass().getName() + "|| fetchBill Response  || " + resp);
            e.printStackTrace();
        }
        System.out.println("FetchBill Response  = " + resp);
        Config.debuglog.writeLog(getClass().getName() + "|| FetchBill Response  || " + resp);
        return resp;
    }

    @Override
    public String getSaveData(String req) {
        SavebillReqBean savereq;
        String res = "";
        try {
            savereq = Utils.fromJson(req, SavebillReqBean.class);
            Config.debuglog.writeLog(getClass().getName() + "|| getSaveData  || " + req);
            System.out.println("getSaveData  || " + req);
            
            if (isInvalidSaveDataRequest(savereq)) {
                res = "{\"responseCode\":\"5001\",\"status\":\"FAILED\",\"responseMessage\":\"Mandatory Parameter Missing\"}";
            } else {
                String query = "insert into tr_table_etisalat_save(servicekey,transactionid,amountdue,amountpaid,msisdn,status,status_desc,inti_time)"
                        + " values('" + savereq.getServicekey() + "','" + savereq.getTransactionID() + "','"
                        + savereq.getAmountDue() + "','" + savereq.getAmountPaid() + "','" + savereq.getMsisdn()
                        + "',1,'Save Successfull',sysdate)";
                res = dbOperations.insertTransaction(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = "{\"responseCode\":\"5000\",\"status\":\"FAILED\",\"responseMessage\":\"Internal Server Error\"}";
        }
        System.out.println(res);
        return res;
    }

    @Override
    public String payment(String request) {
        String response = "";
        BillPayRequestForAll billPayReq = DeSignedData.Signed_Dec_text(request);
        try {
            Config.debuglog.writeLog(getClass().getName() + "|| Payment Request " + billPayReq);
            System.out.println("Payment Request " + billPayReq);
            response = processPayment.processPayment(billPayReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String generateCDR() {
        String resp = null;
        try {
            resp = sendMail.doSaveAsCSV();
            System.out.println("generateCDR " + resp);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILURE";
        }
        return null;
    }

    private boolean isInvalidFetchBillRequest(FetchbillReqBeanJson reqBean) {
        return reqBean.msisdn == null || reqBean.msisdn.equals("null") || reqBean.msisdn.isEmpty()
                || reqBean.nationalno == null || reqBean.nationalno.equals("null") || reqBean.nationalno.isEmpty()
                || reqBean.servicekey == null || reqBean.servicekey.equals("null") || reqBean.servicekey.isEmpty()
                || reqBean.serviceType == null || reqBean.serviceType.equals("null") || reqBean.serviceType.isEmpty();
    }

    private boolean isInvalidSaveDataRequest(SavebillReqBean savereq) {
        return savereq.getTransactionID() == null || savereq.getTransactionID().isEmpty()
                || savereq.getTransactionID().equals("null") || savereq.getMsisdn() == null
                || savereq.getMsisdn().isEmpty() || savereq.getMsisdn().equals("null")
                || savereq.getAmountPaid() == null || savereq.getAmountPaid().isEmpty()
                || savereq.getAmountPaid().equals("null") || savereq.getAmountDue() == null
                || savereq.getAmountDue().isEmpty() || savereq.getAmountDue().equals("null")
                || savereq.getServicekey() == null || savereq.getServicekey().isEmpty()
                || savereq.getServicekey().equals("null");
    }
}
