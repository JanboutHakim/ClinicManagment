package com.example.DocLib.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// Add this DTO class
@Getter
@Setter
public  class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}

