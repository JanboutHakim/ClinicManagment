package com.example.DocLib.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DocLib.configruation.UserPrincipleConfig;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {
    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternalSetsAuthentication() throws Exception {
        JwtDecoder decoder = mock(JwtDecoder.class);
        JwtPrincipleConverter converter = mock(JwtPrincipleConverter.class);
        DecodedJWT jwt = mock(DecodedJWT.class);
        when(decoder.decode("token")).thenReturn(jwt);
        when(jwt.getClaim("type").asString()).thenReturn("access");
        UserPrincipleConfig cfg = UserPrincipleConfig.builder()
                .userId(2L)
                .username("user")
                .authorities(Collections.emptyList())
                .build();
        when(converter.convert(jwt)).thenReturn(cfg);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(decoder, converter);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UserPrincipleAuthenticationToken);
        verify(chain).doFilter(request, response);
    }
}
