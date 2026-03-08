package com.malgn.content.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ContentCreateRequest(

    @NotBlank(message = "제목은 필수 입니다.")
    String title,

    @NotBlank(message = "내용은 필수 입니다.")
    String description
) {

}
