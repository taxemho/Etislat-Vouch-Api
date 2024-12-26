package com.pyro.ets.bean;

import org.springframework.stereotype.Component;

@Component
public class Reply {
	private String ReplyDateStamp;

	private String TransactionType;

	private double AmountDue;

	private String TransactionDateStamp;

	private String ServiceType;

	private String NationalNo;

	private String ErrorCode;

	private String ErrorMessage;

	private String BankID;

	private String TransactionID;

	public String getReplyDateStamp() {
		return ReplyDateStamp;
	}

	public void setReplyDateStamp(String replyDateStamp) {
		ReplyDateStamp = replyDateStamp;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public double getAmountDue() {
		return AmountDue;
	}

	public void setAmountDue(double amountDue) {
		AmountDue = amountDue;
	}

	public String getTransactionDateStamp() {
		return TransactionDateStamp;
	}

	public void setTransactionDateStamp(String transactionDateStamp) {
		TransactionDateStamp = transactionDateStamp;
	}

	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public String getNationalNo() {
		return NationalNo;
	}

	public void setNationalNo(String nationalNo) {
		NationalNo = nationalNo;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

	public String getBankID() {
		return BankID;
	}

	public void setBankID(String bankID) {
		BankID = bankID;
	}

	public String getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}

	@Override
	public String toString() {
		return "Reply [ReplyDateStamp=" + ReplyDateStamp + ", TransactionType=" + TransactionType + ", AmountDue="
				+ AmountDue + ", TransactionDateStamp=" + TransactionDateStamp + ", ServiceType=" + ServiceType
				+ ", NationalNo=" + NationalNo + ", ErrorCode=" + ErrorCode + ", ErrorMessage=" + ErrorMessage
				+ ", BankID=" + BankID + ", TransactionID=" + TransactionID + "]";
	}

	
}