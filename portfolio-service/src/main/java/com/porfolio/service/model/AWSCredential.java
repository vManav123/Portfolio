package com.porfolio.service.model;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aws")
public record AWSCredential(String accessKey, String secretKey, String region) {
}
