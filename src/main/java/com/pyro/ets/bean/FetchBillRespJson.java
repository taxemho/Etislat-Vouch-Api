package com.pyro.ets.bean;

public class FetchBillRespJson {

	private String transactionID;
	private String serviceType;
	private String nationalNo;
	private String amountDue;
	private String replyDateStamp;
	private String transactionDateStamp;
	private String errorCode;
	private String errorMessage;
	private String responseCode;
	private String responseMessage;
	private String service_charges;

	// Getters and Setters
	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getNationalNo() {
		return nationalNo;
	}

	public void setNationalNo(String nationalNo) {
		this.nationalNo = nationalNo;
	}

	public String getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}

	public String getReplyDateStamp() {
		return replyDateStamp;
	}

	public void setReplyDateStamp(String replyDateStamp) {
		this.replyDateStamp = replyDateStamp;
	}

	public String getTransactionDateStamp() {
		return transactionDateStamp;
	}

	public void setTransactionDateStamp(String transactionDateStamp) {
		this.transactionDateStamp = transactionDateStamp;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage= responseMessage ;
  }

	public String getService_charges() {
		return service_charges;
	}

	public void setService_charges(String service_charges) {
		this.service_charges = service_charges;
	}

	
   }
