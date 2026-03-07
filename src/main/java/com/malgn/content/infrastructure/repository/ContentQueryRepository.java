package com.malgn.content.infrastructure.repository;

import com.malgn.content.domain.entity.Content;
import com.malgn.content.domain.entity.QContent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Content> findAllContents(Pageable pageable) {

        QContent content = QContent.content;

        List<Content> contents = queryFactory
            .selectFrom(content)
            .orderBy(content.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(content.count())
            .from(content)
            .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    public void increaseViewCount(Long contentId) {

        QContent content = QContent.content;

        queryFactory
            .update(content)
            .set(content.viewCount, content.viewCount.add(1))
            .where(content.id.eq(contentId))
            .execute();
    }
}
