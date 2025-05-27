package com.example.DocLib.models.authentication;

import com.example.DocLib.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private final UserDto userDto;
    private final String massage;
}
