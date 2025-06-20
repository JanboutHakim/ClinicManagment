package com.example.DocLib.controllers;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.authentication.LoginRequest;
import com.example.DocLib.models.authentication.TokenResponse;
import com.example.DocLib.dto.RefreshTokenRequest;
import com.example.DocLib.services.implementation.AuthServices;
import com.example.DocLib.services.implementation.UserServicesImp;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Test
    void registerDelegatesToService() {
        UserServicesImp userServices = mock(UserServicesImp.class);
        AuthServices authServices = mock(AuthServices.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthController controller = new AuthController(userServices, authServices, encoder);

        UserDto dto = new UserDto();
        when(authServices.registerUser(dto)).thenReturn(dto);

        ResponseEntity<?> response = controller.register(dto);

        assertTrue(response.getBody().toString().contains("User registered"));
        verify(authServices).registerUser(dto);
    }

    @Test
    void loginUserReturnsToken() {
        UserServicesImp userServices = mock(UserServicesImp.class);
        AuthServices authServices = mock(AuthServices.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthController controller = new AuthController(userServices, authServices, encoder);

        LoginRequest request = new LoginRequest();
        request.setUsername("u");
        request.setPassword("p");
        TokenResponse token = new TokenResponse();
        when(authServices.attemptLogin("u","p")).thenReturn(token);

        ResponseEntity<TokenResponse> result = controller.loginUser(request);

        assertSame(token, result.getBody());
        verify(authServices).attemptLogin("u","p");
    }

    @Test
    void refreshTokenUsesService() {
        UserServicesImp userServices = mock(UserServicesImp.class);
        AuthServices authServices = mock(AuthServices.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthController controller = new AuthController(userServices, authServices, encoder);

        TokenResponse token = new TokenResponse();
        when(authServices.refreshToken("r")).thenReturn(token);

        ResponseEntity<TokenResponse> result = controller.refreshToken(new RefreshTokenRequest("r"));

        assertSame(token, result.getBody());
        verify(authServices).refreshToken("r");
    }
}
