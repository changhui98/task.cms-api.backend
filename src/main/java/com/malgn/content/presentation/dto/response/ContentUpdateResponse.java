package com.malgn.content.presentation.dto.response;

import com.malgn.content.domain.entity.Content;
import java.time.LocalDateTime;

public record ContentUpdateResponse(
    Long id,
    String title,
    String description,
    String lastModifiedBy,
    LocalDateTime lastModifiedDate
) {

    public static ContentUpdateResponse from(Content content) {
        return new ContentUpdateResponse(
            content.getId(),
            content.getTitle(),
            content.getDescription(),
            content.getLastModifiedBy(),
            content.getLastModifiedDate()
        );
    }

}
