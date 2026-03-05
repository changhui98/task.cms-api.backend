package com.malgn.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleUserException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse response = new ApiResponse(
            errorCode.getCode(),
            errorCode.getMessage()
        );

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

}
