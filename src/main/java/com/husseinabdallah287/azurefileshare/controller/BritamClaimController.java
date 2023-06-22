package com.husseinabdallah287.azurefileshare.controller;

import com.husseinabdallah287.azurefileshare.service.BritamClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("britamClaims")
public class BritamClaimController {
    @Autowired
    private BritamClaimService britamClaimService;

    @GetMapping(value = "/sendBritamClaims")
    public String sendBritamClaims() throws SQLException {
        return britamClaimService.getBritamClaims();
    }

}
