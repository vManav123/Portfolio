package com.porfolio.service.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.porfolio.service.model.AWSCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AWSConfiguration {

    @Autowired
    private AWSCredential awsCredential;

    @Bean
    @Primary
    public AmazonS3 generateS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(awsCredential.accessKey(),awsCredential.secretKey());
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsCredential.region())
                .build();
    }
}
