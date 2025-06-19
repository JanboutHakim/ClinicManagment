package com.example.DocLib.models.authentication;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private String message;
    private Boolean success;
    private long expiresIn;
    private UserDto user;
}