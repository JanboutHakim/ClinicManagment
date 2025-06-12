package com.example.DocLib.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DocLib.configruation.UserPrincipleConfig;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtHandshakeInterceptorTest {
    @Test
    void beforeHandshakeSetsPrincipalAttribute() {
        JwtDecoder decoder = mock(JwtDecoder.class);
        JwtPrincipleConverter converter = mock(JwtPrincipleConverter.class);
        DecodedJWT jwt = mock(DecodedJWT.class);

        when(decoder.decode("token")).thenReturn(jwt);
        when(jwt.getClaim("type").asString()).thenReturn("access");
        UserPrincipleConfig cfg = UserPrincipleConfig.builder()
                .userId(1L)
                .username("test")
                .authorities(Collections.emptyList())
                .password(null)
                .userRepository(null)
                .build();
        when(converter.convert(jwt)).thenReturn(cfg);

        JwtHandshakeInterceptor interceptor = new JwtHandshakeInterceptor(decoder, converter);
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.addHeader("Authorization", "Bearer token");
        ServletServerHttpRequest request = new ServletServerHttpRequest(servletRequest);
        ServletServerHttpResponse response = new ServletServerHttpResponse(new MockHttpServletResponse());
        HashMap<String, Object> attributes = new HashMap<>();

        boolean result = interceptor.beforeHandshake(request, response, null, attributes);
        assertTrue(result);
        assertTrue(attributes.get("principal") instanceof UserPrincipleAuthenticationToken);
    }

    @Test
    void handshakeRejectedWithoutToken() {
        JwtDecoder decoder = mock(JwtDecoder.class);
        JwtPrincipleConverter converter = mock(JwtPrincipleConverter.class);

        JwtHandshakeInterceptor interceptor = new JwtHandshakeInterceptor(decoder, converter);
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        ServletServerHttpRequest request = new ServletServerHttpRequest(servletRequest);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        ServletServerHttpResponse response = new ServletServerHttpResponse(servletResponse);
        HashMap<String, Object> attributes = new HashMap<>();

        boolean result = interceptor.beforeHandshake(request, response, null, attributes);
        assertFalse(result);
        assertEquals(401, servletResponse.getStatus());
    }
}