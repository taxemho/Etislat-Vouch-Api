package com.pyro.ets.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CallBackResponseData")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallBackResponseData {

    @XmlElement(name = "TransactionId", nillable = true, required = true)
    private String transactionId;

    @XmlElement(name = "status", nillable = true, required = true)
    private String status;

    @XmlElement(name = "srcmsisdn", nillable = true, required = true)
    private String srcmsisdn;

    @XmlElement(name = "destmsisdn", nillable = true, required = true)
    private String destmsisdn;

    @XmlElement(name = "description", nillable = true, required = true)
    private String description;

    @XmlElement(name = "amount", nillable = true, required = true)
    private String amount;

    @XmlElement(name = "operatorid", nillable = true, required = true)
    private String operatorid;

    @XmlElement(name = "optional1", nillable = true, required = true)
    private String optional1;

    @XmlElement(name = "optional2", nillable = true, required = true)
    private String optional2;

    @XmlElement(name = "optional3", nillable = true, required = true)
    private String optional3;

    @XmlElement(name = "object", nillable = true, required = true)
    private Object object;

    // Getters and Setters
    // (No changes needed; they are already provided in the original code)

    @Override
    public String toString() {
        return "CallBackResponseData [transactionId=" + transactionId + ", status=" + status + ", srcmsisdn=" + srcmsisdn
                + ", destmsisdn=" + destmsisdn + ", description=" + description + ", amount=" + amount
                + ", operatorid=" + operatorid + ", optional1=" + optional1 + ", optional2=" + optional2
                + ", optional3=" + optional3 + "]";
    }
}
