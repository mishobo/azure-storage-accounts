package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

@Data
public class FileUploadResponse {

    private String fileName;
    private String downloadUri;
    private long size;

    public FileUploadResponse() {

    }

    public FileUploadResponse(String fileName, long size,String downloadUri) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.size = size;
    }
}
