package com.example.DocLib.exceptions.custom;


public class ResourceNotFoundException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
