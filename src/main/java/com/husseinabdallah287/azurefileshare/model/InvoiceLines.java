package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceLines {

    private String activityCode;
    private BigDecimal requestedPrice;
    private String requestedCurrency;
    private String activityName;
    private int quantity;
    private String GUID;

}
