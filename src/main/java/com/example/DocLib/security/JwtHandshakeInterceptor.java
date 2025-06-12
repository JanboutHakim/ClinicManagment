package com.example.DocLib.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtDecoder jwtDecoder;
    private final JwtPrincipleConverter jwtPrincipleConverter;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                var jwt = jwtDecoder.decode(token);
                if ("access".equals(jwt.getClaim("type").asString())) {
                    var principal = jwtPrincipleConverter.convert(jwt);
                    var authentication = new UserPrincipleAuthenticationToken(principal);
                    attributes.put("principal", authentication);
                }
            } catch (Exception ignored) {
                // ignore invalid token; handshake proceeds without authentication
            }
        }
            // reject handshake if no valid token
            if (response instanceof org.springframework.http.server.ServletServerHttpResponse httpResponse) {
                httpResponse.getServletResponse().setStatus(401);
            }
            return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // no-op
    }
}
