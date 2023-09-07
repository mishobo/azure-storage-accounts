package com.husseinabdallah287.azurefileshare.auth;

import com.husseinabdallah287.azurefileshare.fileUpload.FileUploadUtil;
import com.husseinabdallah287.azurefileshare.model.KenGenDrives;
import com.husseinabdallah287.azurefileshare.model.KenGenToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.apache.logging.log4j.message.ParameterizedMessage.format;

@Service
public class AuthenticationProvider {

    @Value("${ken-gen.client.id}")
    private String clientId;

    @Value("${ken-gen.client.secret}")
    private String clientSecret;

    @Value("${ken-gen.tenant.id}")
    private String tenantId;

    @Value("${ken-gen.scope}")
    private String scope;

    private List<String> scopes = Arrays.asList(scope);



//    public IGraphServiceClient getAuthProvider() {
//        IAuthenticationProvider mAuthenticationProvider;
//
//        try {
//            //String accessToken = getAuthToken();
//        } catch (Exception e) {
//            throw new Error("Could not create a graph client: " + e.getLocalizedMessage());
//        }
//
//    }

    public void getAuthToken(String filePath) throws IOException {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "69657438-450f-4961-8c7e-f418a59a5077"); // client_secret
        formData.add("scope", "https://graph.microsoft.com/.default");
        formData.add("client_secret", "~bc8Q~ol0ZkiAaN0Tv9Lu-zCQJt7KD7m0pza1bso");

        KenGenToken token = WebClient.create()
                .post()
                .uri("https://login.microsoftonline.com/e5874554-0653-45ab-9cc7-a456e2147e46/oauth2/v2.0/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .block()
                .bodyToMono(KenGenToken.class)
                .block();

        System.out.println(token.getAccess_token());


        KenGenDrives drives = WebClient.create()
                .get()
                .uri("https://graph.microsoft.com/v1.0/drives")
                .headers(h -> h.setBearerAuth(token.getAccess_token()))
                .exchange()
                .block()
                .bodyToMono(KenGenDrives.class)
                .block();

        System.out.println(drives.getValue());

        drives.getValue().forEach(
                value ->
                        System.out.println(value.getId()
                        ));

        System.out.println("filePath :" + filePath);

        var bytes = new FileSystemResource((filePath)).getInputStream()
                .readAllBytes();

//        MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        builder.part("file", multipartFile.getResource());
//
        String uploadFile = WebClient.create()
                .put()
                .uri("https://graph.microsoft.com/v1.0/drives/" +
                        "b!enjU1lkSG0aytcjBa72uMThP1IrLvyJCjpIof8V62Zhw0vWJYTSKSYkx4sWhdDgt" +
                        "/items/root:/LCT Medical Reports/007/sample.pdf:/content")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token.getAccess_token()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .bodyValue(bytes)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        System.out.println(uploadFile);
    }


}
