package com.malgn.content.presentation.controller;

import com.malgn.content.application.service.ContentService;
import com.malgn.content.presentation.dto.request.ContentCreateRequest;
import com.malgn.content.presentation.dto.request.ContentUpdateRequest;
import com.malgn.content.presentation.dto.response.ContentCreateResponse;
import com.malgn.content.presentation.dto.response.ContentDetailResponse;
import com.malgn.content.presentation.dto.response.ContentResponse;
import com.malgn.content.presentation.dto.response.ContentUpdateResponse;
import com.malgn.global.configure.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping
    public ResponseEntity<Page<ContentResponse>> getContents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<ContentResponse> res = contentService.getContents(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDetailResponse> getContent(
        @PathVariable Long contentId,
        @AuthenticationPrincipal CustomUser user
    ) {
        ContentDetailResponse res = contentService.getContent(contentId, user);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ContentCreateResponse> createContent(
        @RequestBody @Valid ContentCreateRequest req,
        @AuthenticationPrincipal CustomUser user
    ) {
        ContentCreateResponse res = contentService.createContent(req, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping("/{contentId}")
    public ResponseEntity<ContentUpdateResponse> updateContent(
        @PathVariable Long contentId,
        @RequestBody ContentUpdateRequest req,
        @AuthenticationPrincipal CustomUser user
    ) {
        ContentUpdateResponse res = contentService.updateContent(contentId, req, user.getUsername(), user.getRole());

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> delectContent(
        @PathVariable Long contentId,
        @AuthenticationPrincipal CustomUser user
    ) {
        contentService.deleteContent(contentId, user.getUsername(), user.getRole());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
