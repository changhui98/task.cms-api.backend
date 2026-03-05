package com.malgn.global.exception;

public record ApiResponse(
    String errorCode,
    String message
) {

}
