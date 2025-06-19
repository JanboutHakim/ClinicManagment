package com.example.DocLib.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Add this DTO class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}

