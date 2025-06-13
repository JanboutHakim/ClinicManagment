package com.example.DocLib.dto;
// Custom Error Response Class

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @NotNull
    private Integer statusCode;

    @NotBlank
    private String message;

    public ErrorResponse(String message)
    {
        super();
        this.message = message;
    }
}