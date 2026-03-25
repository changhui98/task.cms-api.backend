package com.malgn.member.domain.repository;

import com.malgn.member.domain.entity.Member;
import com.malgn.member.presentation.dto.response.MemberListResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepository {

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

    Member save(Member member);

    Page<Member> findAllMembers(Pageable pageable);
}
