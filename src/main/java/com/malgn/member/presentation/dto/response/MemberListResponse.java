package com.malgn.member.presentation.dto.response;

import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.entity.MemberRole;
import java.time.LocalDateTime;

public record MemberListResponse(
    Long id,
    String username,
    MemberRole role,
    LocalDateTime createdAt,
    boolean isDelete
) {

    public static MemberListResponse from(Member member) {
        return new MemberListResponse(
            member.getId(),
            member.getUsername(),
            member.getRole(),
            member.getCreatedDate(),
            member.isDeleted()
        );
    }
}
