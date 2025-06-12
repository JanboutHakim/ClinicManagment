package com.example.DocLib.services.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.DocLib.enums.Roles;
import com.example.DocLib.models.User;
import com.example.DocLib.models.authentication.TokenResponse;
import com.example.DocLib.repositories.UserRepository;
import com.example.DocLib.security.JwtDecoder;
import com.example.DocLib.security.JwtIssuer;
import com.example.DocLib.security.JwtProperties;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServicesTest {
    @Test
    void refreshTokenReturnsNewTokens() {
        JwtProperties props = new JwtProperties();
        props.setSecretKey("secret");

        JwtIssuer issuer = mock(JwtIssuer.class);
        AuthenticationManager am = mock(AuthenticationManager.class);
        ModelMapper mapper = new ModelMapper();
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        JwtDecoder decoder = mock(JwtDecoder.class);

        AuthServices service = new AuthServices(issuer, am, mapper, repo, encoder, decoder, props);

        DecodedJWT jwt = mock(DecodedJWT.class);
        when(decoder.decode("token")).thenReturn(jwt);
        when(jwt.getClaim("type").asString()).thenReturn("refresh");
        when(jwt.getSubject()).thenReturn("1");
        when(jwt.getClaim("u").asString()).thenReturn("user");

        User user = User.builder().id(1L).username("user").role(Roles.ADMIN).build();
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        when(issuer.issueAccessToken(1L, "user", List.of("ROLE_ADMIN"))).thenReturn("a");
        when(issuer.issueRefreshToken(1L, "user")).thenReturn("r");

        TokenResponse response = service.refreshToken("token");

        assertEquals("a", response.getAccessToken());
        assertEquals("r", response.getRefreshToken());
        assertEquals(props.getAccessTokenExpirationMs(), response.getExpiresIn());
    }

    @Test
    void extractUserIdReturnsSubject() {
        JwtProperties props = new JwtProperties();
        props.setSecretKey("secret");
        JwtIssuer issuer = new JwtIssuer(props);
        JwtDecoder decoder = new JwtDecoder(props);
        AuthServices service = new AuthServices(issuer, mock(AuthenticationManager.class), new ModelMapper(),
                mock(UserRepository.class), mock(PasswordEncoder.class), decoder, props);

        String token = issuer.issueAccessToken(2L, "u", List.of("ROLE_USER"));
        Long id = service.extractUserId(token);
        assertEquals(2L, id);
    }
}
