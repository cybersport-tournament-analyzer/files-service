package com.gpoint.files_service.config.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gpoint.files_service.property.AmazonS3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmazonS3Config {

    private final AmazonS3Properties amazonS3Properties;

    @Bean
    public AmazonS3 s3Client() {

        AWSCredentials awsCredentials = new BasicAWSCredentials(amazonS3Properties.getAccessKey(), amazonS3Properties.getSecretKey());

        AmazonS3 clientAmazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonS3Properties.getEndpoint(), "ru-1"))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        amazonS3Properties.getBuckets().forEach((key, value) -> {
            if (!clientAmazonS3.doesBucketExistV2(key)) {
                clientAmazonS3.createBucket(key);
            }
        });

        return clientAmazonS3;
    }
}