package com.example.DocLib.configruation;

import com.example.DocLib.exceptions.ErrorObject;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerConfig {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest wr){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);

    }
}
