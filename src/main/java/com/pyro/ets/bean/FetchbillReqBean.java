package com.pyro.ets.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BalanceInquiry")
@XmlAccessorType(XmlAccessType.FIELD)
public class FetchbillReqBean {

    @XmlElement(name = "NationalNo", nillable = false, required = true)
    private String nationalNo;

    @XmlElement(name = "CustomerID", nillable = false, required = true)
    private String customerID;

    @XmlElement(name = "ChannelID", nillable = false, required = true)
    private String channelID;

    @XmlElement(name = "BranchLocation", nillable = false, required = true)
    private String branchLocation;

    @XmlElement(name = "BankID", nillable = false, required = true)
    private String bankID;

    @XmlElement(name = "SenderID", nillable = false, required = true)
    private String senderID;

    // Getters and Setters
    public String getNationalNo() {
        return nationalNo;
    }

    public void setNationalNo(String nationalNo) {
        this.nationalNo = nationalNo;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
