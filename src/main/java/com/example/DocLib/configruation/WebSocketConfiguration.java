package com.example.DocLib.configruation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.example.DocLib.security.JwtChannelInterceptor;
import com.example.DocLib.security.JwtDecoder;
import com.example.DocLib.security.JwtHandshakeInterceptor;
import com.example.DocLib.security.JwtPrincipleConverter;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    private final JwtDecoder jwtDecoder;
    private final JwtPrincipleConverter jwtPrincipleConverter;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user"); // Important for /user/queue/...
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("ws")
                .addInterceptors(new JwtHandshakeInterceptor(jwtDecoder, jwtPrincipleConverter))
                .setAllowedOrigins("http://127.0.0.1:5500")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor(jwtDecoder, jwtPrincipleConverter));
    }


}
