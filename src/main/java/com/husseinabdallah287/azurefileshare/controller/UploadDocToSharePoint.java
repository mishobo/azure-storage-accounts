package com.husseinabdallah287.azurefileshare.controller;

import com.husseinabdallah287.azurefileshare.auth.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "/sharepoint/gtToken")
    public void getMicrosoftAccess() {
        authenticationProvider.getAuthToken();
    }

}
