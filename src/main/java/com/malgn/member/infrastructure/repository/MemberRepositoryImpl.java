package com.malgn.member.infrastructure.repository;

import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Optional<Member> findByUsername(String username) {

        return memberQueryRepository.findByUsername(username);
    }
}
