package com.example.DocLib.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorObject {
    private String message;
    private HttpStatus statusCode;
    private LocalDateTime timestamp;
}
