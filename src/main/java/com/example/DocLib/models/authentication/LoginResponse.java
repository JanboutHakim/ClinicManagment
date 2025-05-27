package com.example.DocLib.models.authentication;

import com.example.DocLib.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
    private final String statues;
    private final String message;
    private final UserDto userDto;

}

