package com.malgn.content.infrastructure.repository;

import com.malgn.content.domain.entity.Content;
import com.malgn.content.domain.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {

    private final ContentJpaRepository contentJpaRepository;
    private final ContentQueryRepository contentQueryRepository;

    @Override
    public Content save(Content content) {

        return contentJpaRepository.save(content);
    }

    @Override
    public Page<Content> findAllContents(Pageable pageable) {

        return contentQueryRepository.findAllContents(pageable);
    }
}
