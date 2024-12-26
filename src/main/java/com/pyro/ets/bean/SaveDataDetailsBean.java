package com.pyro.ets.bean;

import org.springframework.stereotype.Component;

@Component
public class SaveDataDetailsBean {

    private String service_type;
    private String amountdue;
    private String amountpaid;
    private String msisdn;
    private String servicekey;
    private String nationalno;
    private String code;
    private String trid;

    // Getters and setters remain the same

    @Override
    public String toString() {
        return "SaveDataDetailsBean [service_type=" + service_type + ", amountdue=" + amountdue + ", amountpaid="
                + amountpaid + ", msisdn=" + msisdn + ", servicekey=" + servicekey + ", nationalno=" + nationalno
                + ", code=" + code + ", trid=" + trid + "]";
    }
}
