package com.husseinabdallah287.azurefileshare.model;

import lombok.Data;

@Data
public class KenGenToken {
    private String token_type;
    private Long expires_in;
    private Long ext_expires_in;
    private String access_token;

}
