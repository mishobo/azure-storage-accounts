package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;
@Data
public class Diagnosis {
    private String diagnosisCode;
    private String description;
    private String GUID;

    public Diagnosis(String diagnosisCode, String description, String GUID) {
        this.diagnosisCode = diagnosisCode;
        this.description = description;
        this.GUID = GUID;
    }
}
