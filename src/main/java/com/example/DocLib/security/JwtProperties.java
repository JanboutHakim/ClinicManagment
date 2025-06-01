package com.example.DocLib.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtProperties {
    private String secretKey;
    private long accessTokenExpirationMs = 86400000; // 1 day
    private long refreshTokenExpirationMs = 2592000000L; // 30 days
}
