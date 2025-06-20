package com.example.DocLib.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String otp;
}
