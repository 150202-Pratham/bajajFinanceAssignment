package com.bajaj.api.Bajaj.API.keys.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.bajaj.api.Bajaj.API.keys.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private final String EMAIL = "aryan0133.be23@chitkara.edu.in";
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgument(IllegalArgumentException e) {
        ApiResponse response = new ApiResponse(false, EMAIL, null, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        ApiResponse response = new ApiResponse(false, EMAIL, null, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}