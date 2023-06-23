package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceLines {

    private String activityCode;
    private Double requestedPrice;
    private String requestedCurrency;
    private String activityName;
    private int quantity;
    private String GUID;

    public InvoiceLines(String activityCode, Double requestedPrice, String requestedCurrency, String activityName, int quantity, String GUID) {
        this.activityCode = activityCode;
        this.requestedPrice = requestedPrice;
        this.requestedCurrency = requestedCurrency;
        this.activityName = activityName;
        this.quantity = quantity;
        this.GUID = GUID;
    }
}
