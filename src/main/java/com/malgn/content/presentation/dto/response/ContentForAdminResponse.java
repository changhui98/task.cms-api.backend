package com.malgn.content.presentation.dto.response;

import com.malgn.content.domain.entity.Content;
import java.time.LocalDateTime;

public record ContentForAdminResponse(
    Long id,
    String title,
    String createdBy,
    Long viewCount,
    LocalDateTime createdDate,
    String deletedBy,
    LocalDateTime deletedDate
) {

    public static ContentForAdminResponse from(Content content) {
        return new ContentForAdminResponse(
            content.getId(),
            content.getTitle(),
            content.getCreatedBy(),
            content.getViewCount(),
            content.getCreatedDate(),
            content.getDeletedBy(),
            content.getDeletedDate()
        );
    }
}
