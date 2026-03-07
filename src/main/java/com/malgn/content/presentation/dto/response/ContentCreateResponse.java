package com.malgn.content.presentation.dto.response;

import com.malgn.content.domain.entity.Content;
import java.time.LocalDateTime;

public record ContentCreateResponse(
    Long id,
    String title,
    String description,
    String createdBy,
    LocalDateTime createdAt,
    String updatedBy,
    LocalDateTime updatedAt
) {

    public static ContentCreateResponse from(Content content) {
        return new ContentCreateResponse(
            content.getId(),
            content.getTitle(),
            content.getDescription(),
            content.getCreatedBy(),
            content.getCreatedDate(),
            content.getLastModifiedBy(),
            content.getLastModifiedDate()
        );
    }

}
