package com.example.DocLib.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.DocLib.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    public String issueAccessToken(long userId, String username, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getAccessTokenExpirationMs()))
                .withClaim("u", username)
                .withClaim("r", roles)
                .withClaim("type", "access")
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    public String issueRefreshToken(long userId, String username) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpirationMs()))
                .withClaim("u", username)
                .withClaim("type", "refresh")
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}