package com.example.DocLib.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtIssuerTest {
    @Test
    void issueAccessTokenContainsExpectedClaims() {
        JwtProperties props = new JwtProperties();
        props.setSecretKey("secret");
        JwtIssuer issuer = new JwtIssuer(props);

        String token = issuer.issueAccessToken(1L, "user", List.of("USER"));
        var decoded = JWT.require(Algorithm.HMAC256("secret"))
                .build()
                .verify(token);

        assertEquals("1", decoded.getSubject());
        assertEquals("user", decoded.getClaim("u").asString());
        assertEquals(List.of("ROLE_USER"), decoded.getClaim("r").asList(String.class));
        assertEquals("access", decoded.getClaim("type").asString());
    }

    @Test
    void issueRefreshTokenContainsTypeRefresh() {
        JwtProperties props = new JwtProperties();
        props.setSecretKey("secret");
        JwtIssuer issuer = new JwtIssuer(props);

        String token = issuer.issueRefreshToken(5L, "user");
        var decoded = JWT.require(Algorithm.HMAC256("secret"))
                .build()
                .verify(token);

        assertEquals("5", decoded.getSubject());
        assertEquals("user", decoded.getClaim("u").asString());
        assertEquals("refresh", decoded.getClaim("type").asString());
    }
}
