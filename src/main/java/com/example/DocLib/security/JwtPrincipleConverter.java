package com.example.DocLib.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DocLib.configruation.UserPrincipleConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtPrincipleConverter {
    public UserPrincipleConfig convert(DecodedJWT jwt) {
        return UserPrincipleConfig.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .username(jwt.getClaim("u").asString())
                .authorities(extractAuthorityFromClaim(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthorityFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("r");
        if (claim.isNull() || claim.isMissing()) {
            System.err.println("No roles found in JWT token");
            return List.of();
        }
        
        List<String> roles = claim.asList(String.class);
        System.out.println("Roles from JWT: " + roles);
        
        return roles.stream()
                .map(role -> {
                    // Ensure role has ROLE_ prefix
                    String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    System.out.println("Creating authority: " + authority);
                    return new SimpleGrantedAuthority(authority);
                })
                .toList();
    }
}