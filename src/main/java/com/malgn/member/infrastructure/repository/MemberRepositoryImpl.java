package com.malgn.member.infrastructure.repository;

import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Page<Member> findAllMembers(Pageable pageable) {

        return memberQueryRepository.findAllMembers(pageable);
    }

    @Override
    public Optional<Member> findByUsername(String username) {

        return memberQueryRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {

        return memberJpaRepository.existsByUsername(username);
    }

    @Override
    public Member save(Member member) {

        return memberJpaRepository.save(member);
    }
}
