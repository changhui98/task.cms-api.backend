package com.malgn.content.application.service;

import com.malgn.content.domain.ContentErrorCode;
import com.malgn.content.domain.entity.Content;
import com.malgn.content.domain.entity.ContentView;
import com.malgn.content.domain.repository.ContentRepository;
import com.malgn.content.domain.repository.ContentViewRepository;
import com.malgn.content.presentation.dto.request.ContentCreateRequest;
import com.malgn.content.presentation.dto.request.ContentUpdateRequest;
import com.malgn.content.presentation.dto.response.ContentCreateResponse;
import com.malgn.content.presentation.dto.response.ContentDetailResponse;
import com.malgn.content.presentation.dto.response.ContentResponse;
import com.malgn.content.presentation.dto.response.ContentUpdateResponse;
import com.malgn.global.configure.CustomUser;
import com.malgn.global.exception.AppException;
import com.malgn.member.domain.MemberErrorCode;
import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.entity.MemberRole;
import com.malgn.member.domain.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final ContentViewRepository contentViewRepository;

    @Transactional
    public ContentCreateResponse createContent(ContentCreateRequest req, CustomUser user) {

        Member member = findMember(user.getUsername());

        Content content = Content.of(req.title(), req.description(), member.getUsername());

        Content saveContent = contentRepository.save(content);

        return new ContentCreateResponse(
            saveContent.getId(),
            saveContent.getTitle(),
            saveContent.getDescription(),
            saveContent.getCreatedDate(),
            saveContent.getCreatedBy(),
            saveContent.getLastModifiedDate(),
            saveContent.getLastModifiedBy()
        );
    }

    @Transactional(readOnly = true)
    public Page<ContentResponse> getContents(int page, int size) {

        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(Direction.DESC, "createdDate")
        );

        return contentRepository
            .findAllContents(pageable)
            .map(ContentResponse::from);
    }

    @Transactional
    public ContentDetailResponse getContent(Long contentId, CustomUser user) {

        Member member = findMember(user.getUsername());

        Content content = contentRepository.findById(contentId).orElseThrow(
            () -> new AppException(ContentErrorCode.CONTENT_NOT_FOUND)
        );

        increaseViewCount(content, member.getUsername());

        return ContentDetailResponse.from(content);

    }

    @Transactional
    public ContentUpdateResponse updateContent(Long contentId, ContentUpdateRequest req,
        String username, MemberRole role) {

        if (req.title() == null && req.description() == null) {
            throw new AppException(ContentErrorCode.NOTHING_TO_UPDATE);
        }

        Content content = findContent(contentId);
        Member member = findMember(username);

        validateAuthority(content, member.getUsername(), member.getRole().toString());

        content.update(req.title(), req.description(), member.getUsername());

        return ContentUpdateResponse.from(content);
    }

    private Content findContent(Long contentId) {

        return contentRepository.findById(contentId).orElseThrow(
            () -> new AppException(ContentErrorCode.CONTENT_NOT_FOUND)
        );
    }

    private Member findMember(String username) {
        return memberRepository.findByUsername(username).orElseThrow(
            () -> new AppException(MemberErrorCode.MEMBER_NOT_FOUND)
        );
    }

    private void increaseViewCount(Content content, String username) {

        Optional<ContentView> view = contentViewRepository.findByContentIdAndUsername(
            content.getId(), username);

        if (view.isEmpty()) {

            ContentView contentView = ContentView.of(content.getId(), username);
            contentViewRepository.save(contentView);

            content.increaseViewCount();
            return;
        }

        ContentView existingView = view.get();

        boolean is24HoursPassed =
            existingView.getViewedAt().isBefore(LocalDateTime.now().minusHours(24));

        if (is24HoursPassed) {

            existingView.updateViewedAt();

            content.increaseViewCount();
        }
    }

    private void validateAuthority(Content content, String username, String role) {
        if ("ADMIN".equals(role)) {
            return;
        }

        if ("USER".equals(role) && content.isOwner(username)) {
            return;
        }

        throw new AppException(ContentErrorCode.CONTENT_UPDATE_FORBIDDEN);
    }
}
