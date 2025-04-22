package com.gpoint.files_service.service.s3;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface AmazonS3Service {

    CompletableFuture<String> uploadFile(String path, MultipartFile file, String bucketName);

    CompletableFuture<Resource> downloadFile(String key, long offset, long length, String bucketName);

    CompletableFuture<Resource> downloadFile(String key, String bucketName);

    void deleteFile(String key, String bucketName);
}
