package com.malgn.content.presentation.dto.response;

import java.time.LocalDateTime;

public record ContentCreateResponse(
    Long id,
    String title,
    String description,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy
) {

}
