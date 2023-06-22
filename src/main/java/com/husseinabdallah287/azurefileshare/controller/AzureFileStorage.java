package com.husseinabdallah287.azurefileshare.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.file.share.*;
import com.husseinabdallah287.azurefileshare.fileUpload.FileUploadUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
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

    @GetMapping(value = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }


    @PostMapping(value = "/fileShare/upload1")
    public String fileShare1(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String filePath = "./Files-Upload/" + FileUploadUtil.saveFile(fileName, multipartFile);
        String dirName = "015";

        try {
            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(connectionString)
                    .shareName(shareName)
                    .resourcePath(dirName)
                    .buildDirectoryClient();

            dirClient.createIfNotExists();

            long size = multipartFile.getSize();
            System.out.println("file size : " + size);


            ShareFileClient fileClient = dirClient.getFileClient(fileName);
            fileClient.create(size);
            fileClient.uploadFromFile(filePath);
            return "successful";
        } catch (Exception e) {
            System.err.println("Error uploading the file: " + e.getMessage());
            return "failed";
        }
    }

    @PostMapping(value = "/fileShare/upload")
    public String fileShare(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String shareURL = String.format("https://mishobo.file.core.windows.net", "mishobo");
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String filePath = "./Files-Upload/" + FileUploadUtil.saveFile(fileName, multipartFile);

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
                    .resourcePath("011")
                    .buildDirectoryClient();

            directoryClient.createIfNotExists();

            ShareFileClient fileClient = new ShareFileClientBuilder()
                    .connectionString(connectionString)
                    .endpoint(shareURL)
                    .shareName(shareName)
                    .resourcePath("011")
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


    public String base64String() throws IOException {
        String filePath = "./Files-Upload/invoice_1.pdf";
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));

        return Base64.getEncoder().encodeToString(fileContent);
    }


}
