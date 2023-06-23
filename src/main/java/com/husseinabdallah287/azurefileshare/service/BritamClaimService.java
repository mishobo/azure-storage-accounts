package com.husseinabdallah287.azurefileshare.service;

import com.husseinabdallah287.azurefileshare.model.Req;
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

    public Req getBritamClaims(int visitNumber) throws SQLException {
        return con.connectToDB(
                visitNumber,
                repository.getBritamInvoiceHeader,
                repository.getBritamInvoice,
                repository.getBritamInvoiceLines,
                repository.getDiagnosis
        );
    }

}
