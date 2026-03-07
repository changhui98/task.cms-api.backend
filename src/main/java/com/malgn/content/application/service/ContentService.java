package com.malgn.content.application.service;

import com.malgn.content.domain.entity.Content;
import com.malgn.content.domain.repository.ContentRepository;
import com.malgn.content.presentation.dto.request.ContentCreateRequest;
import com.malgn.content.presentation.dto.response.ContentCreateResponse;
import com.malgn.content.presentation.dto.response.ContentResponse;
import com.malgn.global.configure.CustomUser;
import com.malgn.global.exception.AppException;
import com.malgn.member.domain.MemberErrorCode;
import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.repository.MemberRepository;
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

    private Member findMember(String username) {
        return memberRepository.findByUsername(username).orElseThrow(
            () -> new AppException(MemberErrorCode.MEMBER_NOT_FOUND)
        );
    }
}
