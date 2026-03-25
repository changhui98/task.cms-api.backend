package com.malgn.member.infrastructure.repository;

import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Member> findByUsername(String username) {

        QMember member = QMember.member;

        Member result = queryFactory
            .selectFrom(member)
            .where(member.username.eq(username))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    public Page<Member> findAllMembers(Pageable pageable) {

        QMember member = QMember.member;

        List<Member> members = queryFactory
            .selectFrom(member)
            .orderBy(member.id.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(member.count())
            .from(member)
            .fetchOne();

        return new PageImpl<>(members, pageable, total);
    }
}
