package com.pyro.ets.bean;

import org.springframework.stereotype.Component;

@Component
public class SavebillReqBean {
	private String servicekey;
	private String transactionID;
	private String amountDue;
	private String amountPaid;
	private String msisdn;
	private String transactionId;

	public String getServicekey() {
		return servicekey;
	}

	public void setServicekey(String servicekey) {
		this.servicekey = servicekey;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "SavebillReqBean [servicekey=" + servicekey + ", transactionID=" + transactionID + ", amountDue="
				+ amountDue + ", amountPaid=" + amountPaid + ", msisdn=" + msisdn + ", transactionId=" + transactionId
				+ "]";
	}

}
