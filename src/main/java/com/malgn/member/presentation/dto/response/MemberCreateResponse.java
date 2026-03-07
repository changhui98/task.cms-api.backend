package com.malgn.member.presentation.dto.response;

import java.time.LocalDateTime;

public record MemberCreateResponse(
    Long id,
    String username,
    LocalDateTime createdAt
) {

}
