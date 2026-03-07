package com.malgn.content.presentation.dto.response;

import com.malgn.content.domain.entity.Content;
import java.time.LocalDateTime;

public record ContentResponse(
    Long id,
    String title,
    String createdBy,
    Long viewCount,
    LocalDateTime createdDate
) {

    public static ContentResponse from(Content content) {
        return new ContentResponse(
            content.getId(),
            content.getTitle(),
            content.getCreatedBy(),
            content.getViewCount(),
            content.getCreatedDate()
        );
    }

}
