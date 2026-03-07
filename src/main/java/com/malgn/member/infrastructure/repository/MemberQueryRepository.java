package com.malgn.member.infrastructure.repository;

import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
}
