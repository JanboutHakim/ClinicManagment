# Clinic Management System

This project provides a simple clinic management backend built with Spring Boot.  

## WebSocket authentication

WebSocket connections to `/ws` require a valid JWT. A custom
`JwtHandshakeInterceptor` extracts the `Authorization` header during the initial
handshake and creates a `UserPrincipleAuthenticationToken`. If the token is
missing or invalid the handshake is rejected with a `401` status.

A `JwtChannelInterceptor` performs the same validation for STOMP `CONNECT`
frames ensuring messages only arrive from authenticated users.

Clients must include an `Authorization: Bearer <token>` header when opening the
WebSocket connection or sending the STOMP `CONNECT` frame.
