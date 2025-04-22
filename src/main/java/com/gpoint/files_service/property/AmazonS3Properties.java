package com.gpoint.files_service.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;

@Data
@RefreshScope
@ConfigurationProperties(prefix = "services.s3")
public class AmazonS3Properties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private Map<String, String> buckets;
    private Map<String, String> paths;
    private Map<String, Expansion> expansion;

    @Data
    public static class Expansion {
        private int width;
        private int height;
    }
}