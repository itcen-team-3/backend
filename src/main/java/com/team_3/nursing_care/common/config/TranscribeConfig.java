package com.team_3.nursing_care.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.transcribe.TranscribeClient;

@Configuration
public class TranscribeConfig {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String aws_access_key;
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String aws_secret_key;

    @Bean
    public TranscribeClient transcribeClient() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(aws_access_key, aws_secret_key);
        return TranscribeClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
