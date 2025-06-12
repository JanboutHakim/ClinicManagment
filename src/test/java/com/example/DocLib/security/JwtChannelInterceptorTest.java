package com.example.DocLib.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DocLib.configruation.UserPrincipleConfig;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtChannelInterceptorTest {
    @Test
    void preSendSetsUserOnValidToken() {
        JwtDecoder decoder = mock(JwtDecoder.class);
        JwtPrincipleConverter converter = mock(JwtPrincipleConverter.class);
        DecodedJWT jwt = mock(DecodedJWT.class);
        when(decoder.decode("token")).thenReturn(jwt);
        when(jwt.getClaim("type").asString()).thenReturn("access");
        UserPrincipleConfig cfg = UserPrincipleConfig.builder()
                .userId(10L)
                .username("u")
                .authorities(Collections.emptyList())
                .build();
        when(converter.convert(jwt)).thenReturn(cfg);

        JwtChannelInterceptor interceptor = new JwtChannelInterceptor(decoder, converter);

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECT);
        accessor.addNativeHeader("Authorization", "Bearer token");
        Message<byte[]> message = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());

        Message<?> result = interceptor.preSend(message, mock(MessageChannel.class));
        StompHeaderAccessor resultAccessor = StompHeaderAccessor.wrap(result);
        assertNotNull(resultAccessor.getUser());
        assertTrue(resultAccessor.getUser() instanceof UserPrincipleAuthenticationToken);
    }
}
