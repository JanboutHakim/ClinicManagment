package com.example.DocLib.security;

import com.example.DocLib.configruation.UserPrincipleConfig;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtPrincipleConverter jwtPrincipleConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            extractTokenFromRequest(request)
                    .map(jwtDecoder::decode)
                    .filter(jwt -> "access".equals(jwt.getClaim("type").asString()))
                    .map(jwtPrincipleConverter::convert)
                    .map(UserPrincipleAuthenticationToken::new)
                    .ifPresent(authentication -> {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("Authentication set for user: " + authentication.getPrincipal().getUsername());
                        System.out.println("Authorities: " + authentication.getAuthorities());
                    });
        } catch (JwtException e) {
            System.err.println("JWT validation failed: " + e.getMessage());
            // Clear any existing authentication
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            System.err.println("Unexpected error in JWT filter: " + e.getMessage());
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}