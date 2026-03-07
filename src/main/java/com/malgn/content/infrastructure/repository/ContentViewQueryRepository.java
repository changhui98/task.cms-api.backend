package com.malgn.content.infrastructure.repository;

import com.malgn.content.domain.entity.ContentView;
import com.malgn.content.domain.entity.QContentView;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentViewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<ContentView> findByContentIdAndUsername(Long contentId, String username) {

        QContentView contentView = QContentView.contentView;

        ContentView result = queryFactory
            .selectFrom(contentView)
            .where(
                contentView.contentId.eq(contentId),
                contentView.username.eq(username)
            )
            .fetchOne();

        return Optional.ofNullable(result);
    }
}
