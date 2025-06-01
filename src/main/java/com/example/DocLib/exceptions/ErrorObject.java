package com.example.DocLib.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorObject {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
}
