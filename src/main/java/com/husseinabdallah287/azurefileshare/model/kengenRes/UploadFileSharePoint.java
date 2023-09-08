package com.husseinabdallah287.azurefileshare.model.kengenRes;

import lombok.Data;

@Data
public class UploadFileSharePoint {
    private String context;
    private String downloadUrl;
    private String createdDateTime;
    private String eTag;
    private String id;
    private String lastModifiedDateTime;
    private String name;
    private String webUrl;
    private String cTag;
    private int size;
    private Object createdBy;
    private Object lastModifiedBy;
    private Object parentReference;
    private Object file;
    private Object fileSystemInfo;
    private Object shared;
}
