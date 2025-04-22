package com.gpoint.files_service.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.gpoint.files_service.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Async("s3ExecutorService")
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Override
    public CompletableFuture<String> uploadFile(String path, MultipartFile file, String bucketName) {

        String generated = generateKey();
        return save(path, file, generated, bucketName);
    }

    @Override
    public CompletableFuture<Resource> downloadFile(String key, long offset, long length, String bucketName) {

        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        request.setRange(offset, offset + length - 1);

        S3ObjectInputStream object = amazonS3
                .getObject(request)
                .getObjectContent();

        try {
            return CompletableFuture.completedFuture(new ByteArrayResource(object.readAllBytes()));
        } catch (Exception e) {
            throw new S3Exception(e.getMessage());
        }
    }

    @Override
    public CompletableFuture<Resource> downloadFile(String key, String bucketName) {

        S3ObjectInputStream object = amazonS3
                .getObject(bucketName, key)
                .getObjectContent();

        try {
            return CompletableFuture.completedFuture(new ByteArrayResource(object.readAllBytes()));
        } catch (Exception e) {
            throw new S3Exception(e.getMessage());
        }
    }

    @Override
    public void deleteFile(String key, String bucketName) {
        amazonS3.deleteObject(bucketName, key);
    }

    private String generateKey() {
        return UUID.randomUUID().toString();
    }

    private CompletableFuture<String> save(String path, MultipartFile file, String generated, String bucketName) {
        String key = path + "/" + generated;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setLastModified(Date.from(Instant.now()));

        try {
            amazonS3.putObject(
                    bucketName,
                    key,
                    file.getInputStream(),
                    metadata
            );
        } catch (IOException e) {
            throw new S3Exception(e.getMessage());
        }

        return CompletableFuture.completedFuture(generated);
    }
}
