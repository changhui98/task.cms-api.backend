package com.malgn.member.presentation.dto.response;

import com.malgn.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record MemberCreateResponse(

    @Schema(description = "사용자 고유 번호", example = "1")
    Long id,

    @Schema(description = "사용자 아이디", example = "ryu")
    String username,

    @Schema(description = "가입된 날짜", example = "2026-03-07T20:39:58.267153")
    LocalDateTime createdAt
) {

    public static MemberCreateResponse from(Member member) {
        return new MemberCreateResponse(
            member.getId(),
            member.getUsername(),
            member.getCreatedDate()
        );
    }

}
