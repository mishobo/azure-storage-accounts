package com.husseinabdallah287.azurefileshare.service;

import com.husseinabdallah287.azurefileshare.repository.DBConnection;
import com.husseinabdallah287.azurefileshare.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

@org.springframework.stereotype.Service
public class BritamClaimService {

    @Autowired
    private Repository repository;

    @Autowired
    private DBConnection con;

    public String getBritamClaims() throws SQLException {
        con.connectToDB(repository.getBritamInvoiceHeader);
        return "fetched Britam claims successfully";
    }

}
