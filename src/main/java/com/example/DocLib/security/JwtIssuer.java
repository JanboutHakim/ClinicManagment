package com.example.DocLib.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    public String issueAccessToken(long userId, String username, List<String> roles) {
        // Ensure roles have ROLE_ prefix for consistency
        List<String> formattedRoles = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .toList();
        
        System.out.println("Issuing token for user: " + username + " with roles: " + formattedRoles);
        
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getAccessTokenExpirationMs()))
                .withClaim("u", username)
                .withClaim("r", formattedRoles)
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