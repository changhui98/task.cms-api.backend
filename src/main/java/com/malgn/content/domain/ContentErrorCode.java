package com.malgn.content.domain;

import com.malgn.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContentErrorCode implements ErrorCode {

    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "조회하려는 게시글이 존재하지 않습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
