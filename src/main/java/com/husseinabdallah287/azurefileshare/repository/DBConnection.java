package com.husseinabdallah287.azurefileshare.repository;

import com.husseinabdallah287.azurefileshare.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    @Autowired
    private Repository repository;

    @Autowired
    private WebClient.Builder webClient;


    public Req connectToDB(int visitNumber,String query1, String query2, String query3, String query4) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, visitNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                // get invoice
                PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
                ResultSet rs1 = preparedStatement1.executeQuery();
                if (rs1.next()){
                    // get invoice lines
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query3);
                    preparedStatement2.setInt(1, rs1.getInt("invoice_id"));
                    ResultSet rs2 = preparedStatement2.executeQuery();
                    List<InvoiceLines> invoiceLines = new ArrayList<>();
                    while (rs2.next()){
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
                    ResultSet rs3 = preparedStatement3.executeQuery();
                    List<Diagnosis> diagnoses = new ArrayList<>();
                    while (rs3.next()){
                        Diagnosis diagnosis = new Diagnosis(
                                rs3.getString("code"),
                                rs3.getString("title"),
                                ""
                        );
                        diagnoses.add(diagnosis);
                    }
                    PayerMappings mapping = getPayerMappings(
                            rs.getInt("hospital_provider_id"),
                            rs.getInt("payer_id"),
                            rs.getInt("category_id")
                    );
                    BritamClaimDTO claim =
                            new BritamClaimDTO(
                                    rs.getString("invoice_number"),
                                    rs.getString("invoice_date"),
                                    "Illness",
                                    mapping.getProviderName(),
                                    mapping.getProviderCode(),
                                    rs.getDouble("total_invoice_amount"),
                                    "Kes",
                                    rs.getString("member_name"),
                                    rs.getString("member_number"),
                                    "Inpatient",
                                    2,
                                    "LCT",
                                    "",
                                    mapping.getSchemeName(),
                                    invoiceLines,
                                    diagnoses
                            );

                    Req req = new Req();
                    req.setReq(claim);
                    sendBritamClaim(req);
                    return req;
                }
            }
        } catch (SQLException e) {
            System.out.println("db connection failed");
            e.printStackTrace();
        }
        return null;
    }

    public PayerMappings getPayerMappings(int providerId , int payerId, int CategoryId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(repository.getProviderMapping);
            preparedStatement.setInt(1, providerId);
            preparedStatement.setInt(2, payerId);
            ResultSet rs = preparedStatement.executeQuery();
            PayerMappings mapping = null;
            if (rs.next()) {

                PreparedStatement preparedStatement1 = connection.prepareStatement(repository.getScheme);
                preparedStatement1.setInt(1, CategoryId);
                ResultSet rs1 = preparedStatement1.executeQuery();
                rs1.next();

                mapping = new PayerMappings(
                        rs.getString("provider_name"),
                        rs.getString("code"),
                        rs1.getString("plan_name")
                );
            }
            return mapping;
        } catch(SQLException e) {
           e.printStackTrace();
        }
        return new PayerMappings(404);
    }

    public void sendBritamClaim(Req req){
        System.out.println("request body :" + req);
        WebClient britamClaimClient = WebClient.builder()
                .baseUrl("https://apitest.britam.com/MedicalClaims/ProxyServices/MedicalClaimsProxyServiceRS/claim")
                .build();

        Res res = britamClaimClient
                .post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(req), Res.class)
                .retrieve()
                .bodyToMono(Res.class)
                .block();

        assert res != null;
        System.out.println("invoice Id : " + res.getRes().getInvoiceId());


    }

    public void sendBritamDocuments(String invoiceNumber) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {

            PreparedStatement preparedStatement = connection.prepareStatement(repository.getBritamInvoiceDocument);
            preparedStatement.setString(1, invoiceNumber);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Document doc = webClient.build()
                    .get()
                    .uri("http://34.159.184.24:81/api/file/download?name=" + rs.getString(""))
                    .retrieve()
                    .bodyToMono(Document.class)
                    .block();

            System.out.println("document link :" + doc.getData());

            PreparedStatement preparedStatement1 = connection.prepareStatement(repository.getBritamClaimDocument);
            preparedStatement1.setString(1, invoiceNumber);
            ResultSet rs1 = preparedStatement1.executeQuery();
            rs1.next();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
