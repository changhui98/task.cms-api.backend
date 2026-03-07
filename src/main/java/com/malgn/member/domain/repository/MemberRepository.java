package com.malgn.member.domain.repository;

import com.malgn.member.domain.entity.Member;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByUsername(String username);
}
