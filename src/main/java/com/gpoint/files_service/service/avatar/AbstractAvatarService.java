package com.gpoint.files_service.service.avatar;

import com.gpoint.files_service.classes.ByteArrayMultipartFile;
import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.property.AmazonS3Properties;
import com.gpoint.files_service.service.s3.AmazonS3Service;
import com.gpoint.files_service.util.PictureCompressor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractAvatarService implements AvatarService {

    protected final AmazonS3Properties amazonS3Properties;
    protected final AmazonS3Service amazonS3Service;

    protected abstract AmazonS3Properties.Expansion getExpansion();
    protected abstract String getEntityKey();

    protected ByteArrayMultipartFile compressPic(MultipartFile file) {

        int width = getExpansion().getWidth();
        int height = getExpansion().getHeight();

        try {
            return PictureCompressor.compressPic(file.getInputStream(), file.getName(), width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected File buildFile(MultipartFile file, String key) {
        return File.builder()
                .name(file.getOriginalFilename())
                .key(key)
                .size(file.getSize())
                .type(file.getContentType())
                .build();
    }

    protected final String buildPath(String id) {
        return getEntityKey() + "/" + id + "/" + objectKey;
    }

    protected final String buildPath(String id, String key) {
        return getEntityKey() + "/" + id + "/" + objectKey + "/" + key;
    }

    protected final String getBucketName(String name) {
        return switch (name) {
            case "team-avatar": yield "team-avatar";
            case "tournament-avatar": yield "tournament-avatar";
            case "tournament-banner": yield "tournament-banner";
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
