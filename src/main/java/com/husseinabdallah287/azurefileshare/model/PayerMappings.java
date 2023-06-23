package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

@Data
public class PayerMappings {
    private String providerName;
    private String providerCode;
    private String schemeName;
    private int errorCode;

    public PayerMappings(int errorCode){
        this.errorCode = errorCode;
    }

    public PayerMappings(String providerName, String providerCode, String schemeName) {
        this.providerName = providerName;
        this.providerCode = providerCode;
        this.schemeName = schemeName;
    }
}
