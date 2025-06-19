package com.example.DocLib.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtPrincipleConverterTest {
    @Test
    void convertAddsRolePrefix() {
        String token = JWT.create()
                .withSubject("3")
                .withClaim("u", "alice")
                .withClaim("r", java.util.List.of("ADMIN"))
                .sign(Algorithm.HMAC256("secret"));
        DecodedJWT jwt = JWT.decode(token);

        JwtPrincipleConverter converter = new JwtPrincipleConverter();
        var principal = converter.convert(jwt);

        assertEquals(3L, principal.getUserId());
        assertEquals("alice", principal.getUsername());
        assertEquals(1, principal.getAuthorities().size());
        assertTrue(
                principal.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
        );

    }
}
