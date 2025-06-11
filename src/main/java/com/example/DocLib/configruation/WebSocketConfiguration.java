package com.example.DocLib.configruation;

import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.net.http.WebSocket;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/patient", "/doctor","all","appointments"); // Allow /queue for point-to-point messaging
        config.setApplicationDestinationPrefixes("/ws");
        config.setUserDestinationPrefix("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("ws")
                .setAllowedOrigins("http://127.0.0.1:5500")
                .withSockJS();
    }


}
