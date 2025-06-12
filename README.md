# Clinic Management System

This project provides a simple clinic management backend built with Spring Boot.  

## WebSocket authentication

WebSocket connections to `/ws` now support JWT based authentication. A custom
`JwtHandshakeInterceptor` extracts the `Authorization` header during the initial
handshake and creates a `UserPrincipleAuthenticationToken` which is stored in the
handshake attributes. A `JwtChannelInterceptor` also validates `CONNECT` frames
so STOMP messages have an authenticated principal.

Clients should include a standard `Authorization: Bearer <token>` header when
opening the WebSocket connection or sending the STOMP `CONNECT` frame.
