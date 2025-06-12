package com.example.DocLib.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtDecoderTest {
    @Test
    void decodeValidToken() {
        JwtProperties props = new JwtProperties();
        props.setSecretKey("secret");
        String token = JWT.create()
                .withSubject("42")
                .withClaim("type", "access")
                .sign(Algorithm.HMAC256("secret"));

        JwtDecoder decoder = new JwtDecoder(props);
        var decoded = decoder.decode(token);
        assertEquals("42", decoded.getSubject());
        assertEquals("access", decoded.getClaim("type").asString());
    }
}
