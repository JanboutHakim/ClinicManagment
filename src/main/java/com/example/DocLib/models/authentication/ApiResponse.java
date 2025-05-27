package com.example.DocLib.models.authentication;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ApiResponse<T>   {
    private final T data;
    private final String message;
    private final boolean success;
    private final String accessToken;
}
