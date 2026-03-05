package com.malgn.member.domain;

import com.malgn.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "조회하려는 사용자가 존재하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "U002", "이미 존재하는 아이디입니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
