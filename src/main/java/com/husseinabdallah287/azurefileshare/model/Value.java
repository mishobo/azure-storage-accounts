package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

@Data
public class Value {
    private String createdDateTime;
    private String description;
    private String id;
    private String lastModifiedDateTime;
    private String name;
    private String webUrl;
    private String driveType;
    private Object createdBy;
    private Object lastModifiedBy;
    private Object owner;
    private Object quota;
}
