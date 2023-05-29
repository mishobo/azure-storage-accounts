package com.husseinabdallah287.azurefileshare.controller;

import com.azure.storage.file.share.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("azure")
public class AzureFileStorage {


    @Value("${azure.storage.account.sas.token}")
    private String sasToken;

    @Value("${storage.account.connection}")
    private String connectionString;

    @Value("${azure.storage.account.shareName}")
    private String shareName;


    @PostMapping(value = "/fileShare/upload")
    public String fileShare(){
        String filePath = "E:/projects/Britam/azure-file-share/001/report.pdf";
        String fileName = "report_1.pdf";
        String directoryPath = "008";
        String shareURL = String.format("https://mishobo.file.core.windows.net", "mishobo");

        try {
            ShareClient shareClient = new ShareClientBuilder()
                    .connectionString(connectionString)
                    .endpoint(shareURL)
                    .shareName(shareName)
                    .buildClient();

            shareClient.createDirectoryIfNotExists(shareName);

            ShareDirectoryClient directoryClient = new ShareFileClientBuilder()
                    .connectionString(connectionString)
                    .endpoint(shareURL)
                    .shareName(shareName)
                    .resourcePath(directoryPath)
                    .buildDirectoryClient();

            directoryClient.createIfNotExists();

            ShareFileClient fileClient = new ShareFileClientBuilder()
                    .connectionString(connectionString)
                    .endpoint(shareURL)
                    .shareName(shareName)
                    .resourcePath(directoryPath)
                    .buildFileClient();

            fileClient.uploadFromFile(filePath);
            System.out.println("File uploaded successfully.");
            return "successful";
        } catch (Exception e) {
            System.err.println("Error uploading the file: " + e.getMessage());
            return "failed";
        }
    }




}
