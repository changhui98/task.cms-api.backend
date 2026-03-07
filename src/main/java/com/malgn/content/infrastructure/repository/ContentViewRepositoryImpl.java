package com.malgn.content.infrastructure.repository;

import com.malgn.content.domain.entity.ContentView;
import com.malgn.content.domain.repository.ContentViewRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentViewRepositoryImpl implements ContentViewRepository {

    private final ContentViewJpaRepository contentViewJpaRepository;
    private final ContentViewQueryRepository contentViewQueryRepository;

    @Override
    public Optional<ContentView> findByContentIdAndUsername(Long contentId, String username) {

        return contentViewQueryRepository.findByContentIdAndUsername(contentId, username);
    }

    @Override
    public ContentView save(ContentView contentView) {

        return contentViewJpaRepository.save(contentView);
    }
}
