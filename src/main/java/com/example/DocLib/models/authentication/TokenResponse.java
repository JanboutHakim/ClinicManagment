package com.example.DocLib.models.authentication;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String message;
    private Boolean success;
    private long expiresIn;
    private UserDto user;
}