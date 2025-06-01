package com.example.DocLib.exceptions.custom;

import com.example.DocLib.dto.ErrorResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorResponse apiResponse;

    private String message;

    public AccessDeniedException(ErrorResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public AccessDeniedException(String message) {
        super(message);
        this.message = message;
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }


}