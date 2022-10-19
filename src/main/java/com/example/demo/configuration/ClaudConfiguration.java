package com.example.demo.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ClaudConfiguration {

    @Value("cloudinary.cloud-name")
    private String cloudName;

    @Value("cloudinary.api-key")
    private String apiKey;

    @Value("cloudinary.api-secret")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> configMap = Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret);
        return new Cloudinary(configMap);
    }
}
/**
 * в Binding-модела снимките идват като MultipartFile
 */