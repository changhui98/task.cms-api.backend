package com.malgn.content.domain;

import com.malgn.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContentErrorCode implements ErrorCode {

    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "조회하려는 게시글이 존재하지 않습니다."),
    NOTHING_TO_UPDATE(HttpStatus.BAD_REQUEST, "C002", "업데이트할 것이 없습니다."),
    CONTENT_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "C003", "수정 | 삭제 권한이 없습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
