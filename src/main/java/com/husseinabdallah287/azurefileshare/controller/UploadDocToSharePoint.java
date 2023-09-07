package com.husseinabdallah287.azurefileshare.controller;

import com.husseinabdallah287.azurefileshare.auth.AuthenticationProvider;
import com.husseinabdallah287.azurefileshare.fileUpload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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


//    @RequestMapping(value = "/sharepoint/upload",method = RequestMethod.POST)
//    public String uploadDocToSharePoint() throws Exception {
//        authenticationProvider.setUploadSession();
//
//        return "Successfully uploaded file to sharepoint";
//    }



    @PostMapping("/sharepoint/gtToken")
    public void getMicrosoftAccess(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();
        String filePath = "./Files-Upload/" + FileUploadUtil.saveFile(fileName, multipartFile);

        System.out.println("filePath :" + filePath);

        authenticationProvider.getAuthToken(filePath);
    }

}
