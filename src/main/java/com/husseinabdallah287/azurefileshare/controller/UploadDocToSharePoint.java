package com.husseinabdallah287.azurefileshare.controller;

import com.husseinabdallah287.azurefileshare.auth.AuthenticationProvider;
import com.husseinabdallah287.azurefileshare.fileUpload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("ken-gen")
public class UploadDocToSharePoint {

    @Autowired
    private AuthenticationProvider authenticationProvider;


    @PostMapping("/sharepoint/gtToken")
    public void getMicrosoftAccess(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("providerCode") String providerCode,
            @RequestParam("invoiceName") String invoiceName

    ) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();
        String filePath = "./Files-Upload/" + FileUploadUtil.saveFile(fileName, multipartFile);

        System.out.println("filePath :" + filePath);

        authenticationProvider.uploadFilesTokenGenSharePoint(filePath, providerCode, invoiceName);
    }

}
