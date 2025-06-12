package com.example.DocLib.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtDecoder jwtDecoder;
    private final JwtPrincipleConverter jwtPrincipleConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String auth = accessor.getFirstNativeHeader("Authorization");
            if (StringUtils.hasText(auth) && auth.startsWith("Bearer ")) {
                String token = auth.substring(7);
                try {
                    var jwt = jwtDecoder.decode(token);
                    if ("access".equals(jwt.getClaim("type").asString())) {
                        var principal = jwtPrincipleConverter.convert(jwt);
                        var authentication = new UserPrincipleAuthenticationToken(principal);
                        accessor.setUser(authentication);
                    }
                } catch (Exception ignored) {
                    // ignore invalid token
                }
            }
        }
        return message;
    }
}
