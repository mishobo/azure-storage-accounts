package com.husseinabdallah287.azurefileshare.repository;

import com.google.gson.Gson;
import com.husseinabdallah287.azurefileshare.model.BritamClaimDTO;
import com.husseinabdallah287.azurefileshare.model.InvoiceLines;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBConnection {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    public void connectToDB(String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                System.out.println(rs.getString("visit_number") + rs.getString("benefit_name") +
                        rs.getString("member_name") + rs.getString("member_number"));

                List<InvoiceLines> invoiceLines = new ArrayList<>();
                PreparedStatement preparedStatement1 = connection.prepareStatement(query);

                BritamClaimDTO claim =
                        new BritamClaimDTO(
                                rs.getString("v.invoice_number"),
                                rs.getString("v.invoice_date"),
                                "Illness",
                                "providerName",
                                "providerCode",
                                rs.getDouble("total_invoice_amount"),
                                "Kes",
                                rs.getString("v.member_name"),
                                rs.getString("v.member_number"),
                                "Inpatient",
                                2,
                                "LCT",
                                "",
                                "",
                                null,
                                null
                                );

                Gson britamClaim = new Gson();
                System.out.println("britam json claim :" + britamClaim.toJson(claim));
            }


            System.out.println("db connection successful");
        } catch (SQLException e) {
            System.out.println("db connection failed");
            e.printStackTrace();
        }
    }

}
