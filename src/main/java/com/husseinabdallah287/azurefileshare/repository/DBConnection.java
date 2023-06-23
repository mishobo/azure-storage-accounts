package com.husseinabdallah287.azurefileshare.repository;

import com.google.gson.Gson;
import com.husseinabdallah287.azurefileshare.model.BritamClaimDTO;
import com.husseinabdallah287.azurefileshare.model.Diagnosis;
import com.husseinabdallah287.azurefileshare.model.InvoiceLines;
import com.husseinabdallah287.azurefileshare.model.Req;
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


    public void connectToDB(String query1, String query2, String query3, String query4) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // get invoice header
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                System.out.println(rs.getInt("visit_number") + rs.getString("benefit_name") +
                        rs.getString("member_name") + rs.getString("member_number"));

                // get invoice
                PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
                System.out.println("query2 :" + preparedStatement1);
                ResultSet rs1 = preparedStatement1.executeQuery();
                if (rs1.next()){
                    System.out.println("invoice id :" + rs1.getInt("invoice_id"));
                    // get invoice lines
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query3);
                    preparedStatement2.setInt(1, rs1.getInt("invoice_id"));
                    System.out.println("query3 :" + preparedStatement2);
                    ResultSet rs2 = preparedStatement2.executeQuery();
                    List<InvoiceLines> invoiceLines = new ArrayList<>();
                    while (rs2.next()){
                        System.out.println(rs2.getString("id") + rs2.getString("description") + rs2.getString("line_total"));
                        InvoiceLines line = new InvoiceLines(
                                "Code68",
                                rs2.getDouble("line_total"),
                                "Kes",
                                rs.getString("benefit_name"),
                                rs2.getInt("quantity"),
                                ""
                        );
                        invoiceLines.add(line);
                    }
                    // get diagnosis
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query4);
                    preparedStatement3.setInt(1, rs.getInt("visit_number"));
                    System.out.println("query4 :" + preparedStatement3);
                    ResultSet rs3 = preparedStatement3.executeQuery();
                    List<Diagnosis> diagnoses = new ArrayList<>();
                    while (rs3.next()){
                        System.out.println(rs3.getString("visit_number") + rs3.getString("title") + rs3.getString("code"));
                        Diagnosis diagnosis = new Diagnosis(
                                rs3.getString("code"),
                                rs3.getString("title"),
                                ""
                        );
                        diagnoses.add(diagnosis);
                    }

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
                                    invoiceLines,
                                    diagnoses
                            );

                    Req req = new Req();
                    req.setReq(claim);
                    Gson britamClaim = new Gson();
                    System.out.println("britam json claim :" + britamClaim.toJson(req));
                }
            }
            System.out.println("db connection successful");
        } catch (SQLException e) {
            System.out.println("db connection failed");
            e.printStackTrace();
        } finally {

        }
    }

}
