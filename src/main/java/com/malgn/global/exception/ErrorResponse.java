package com.malgn.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

public record ErrorResponse(
    String code,
    String message,
    @JsonInclude(Include.NON_NULL)List<ErrorFiled> errors
    ) {

    public static ErrorResponse from(AppException appException) {
        return new ErrorResponse(appException.getErrorCode().toString(), appException.getMessage(), null);
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static ErrorResponse of(ErrorCode errorCode, List<ErrorFiled> errors) {
        return  new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errors);
    }

    public record ErrorFiled(Object value, String message) {

    }
}
