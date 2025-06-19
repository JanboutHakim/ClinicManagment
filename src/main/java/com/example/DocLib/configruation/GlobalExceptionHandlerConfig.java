package com.example.DocLib.configruation;

import com.example.DocLib.exceptions.ErrorObject;
import com.example.DocLib.exceptions.custom.AccessDeniedException;
import com.example.DocLib.exceptions.custom.AppointmentNotAvailableAtThisTime;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest wr) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        errorObject.setStatusCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AppointmentNotAvailableAtThisTime.class)
    public ResponseEntity<ErrorObject> handleAppointmentNotAvailableAtThisTime(AppointmentNotAvailableAtThisTime ex, WebRequest wr) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("File is too large! Please upload a smaller file.");
    }


}
