package com.husseinabdallah287.azurefileshare.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.file.share.*;
import com.husseinabdallah287.azurefileshare.fileUpload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

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


    @PostMapping(value = "/blobContainer/upload")
    public String blobContainers(
            @RequestParam("directoryPath") String directoryPath,
            @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();
        String filePath = "./Files-Upload/" + FileUploadUtil.saveFile(fileName, multipartFile);

        System.out.println("filePath :" + filePath);

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient blobContainerClient = blobServiceClient
                .createBlobContainerIfNotExists(shareName);

        BlobClient blobClient = blobContainerClient.getBlobClient(filePath);
        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
        blobClient.uploadFromFile(filePath);


        return "successful upload";
    }


}
