package com.org.apiservices.config;

import com.org.apiservices.util.EncryptionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean
    public EncryptionUtils encryptionUtils() {
        return new EncryptionUtils();
    }
}