package com.pyro.ets.bean;

public class FetchbillReqBeanJson {

    private String nationalno;
    
	private String serviceType;
    
	private String msisdn;
    
	private String servicekey;

	// Getters and Setters
	public String getNationalno() {
		return nationalno;
	}

	public void setNationalno(String nationalno) {
		this.nationalno = nationalno;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		if ("GSM".equalsIgnoreCase(serviceType)) {
			this.serviceType = "POSTPAID";
		} else {
			this.serviceType = serviceType;
		}
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getServicekey() {
		return servicekey;
	}

	public void setServicekey(String servicekey) {
		this.servicekey = servicekey;
	}

	@Override
	public String toString() {
		return "FetchbillReqBeanJson [nationalno=" + nationalno + ", serviceType=" + serviceType 
				+ ", msisdn=" + msisdn + ", servicekey=" + servicekey + "]";
	}
}
