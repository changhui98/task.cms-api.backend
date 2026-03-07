package com.malgn.global.exception;

import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {

        ApiResponse response = new ApiResponse(
            "INTERNAL_SERVER_ERROR",
            "서버 내부 오류가 발생했습니다."
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
