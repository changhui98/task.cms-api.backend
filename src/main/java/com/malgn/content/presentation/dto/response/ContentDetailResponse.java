package com.malgn.content.presentation.dto.response;

import com.malgn.content.domain.entity.Content;

public record ContentDetailResponse(
    Long id,
    String title,
    String description,
    Long viewCount,
    String createdBy
) {

    public static ContentDetailResponse from(Content content) {
        return new ContentDetailResponse(
            content.getId(),
            content.getTitle(),
            content.getDescription(),
            content.getViewCount(),
            content.getCreatedBy()
        );
    }
}
