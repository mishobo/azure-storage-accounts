package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class BritamClaimDTO {
    private String invoiceNumber;
    private String invoiceDate;
    private String eventType;
    private String providerName;
    private String providerCode;
    private double invoiceAmount;
    private String currency;
    private String patientName;
    private String membershipNo;
    private String servicePlace;
    private int networkCode;
    private String systemType;
    private String GUID;
    private String schemeName;
    private List<InvoiceLines> invoiceLines;
    private List<Diagnosis> diagnosis;

    public BritamClaimDTO() {
    }

    public BritamClaimDTO(String invoiceNumber, String invoiceDate, String eventType, String providerName, String providerCode,
                          double invoiceAmount, String currency, String patientName, String membershipNo, String servicePlace,
                          int networkCode, String systemType, String GUID, String schemeName, List<InvoiceLines> invoiceLines, List<Diagnosis> diagnosis) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.eventType = eventType;
        this.providerName = providerName;
        this.providerCode = providerCode;
        this.invoiceAmount = invoiceAmount;
        this.currency = currency;
        this.patientName = patientName;
        this.membershipNo = membershipNo;
        this.servicePlace = servicePlace;
        this.networkCode = networkCode;
        this.systemType = systemType;
        this.GUID = GUID;
        this.schemeName = schemeName;
        this.invoiceLines = invoiceLines;
        this.diagnosis = diagnosis;
    }
}
