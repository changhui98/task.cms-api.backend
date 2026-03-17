package com.malgn.member.application.service;

import com.malgn.global.configure.CustomUser;
import com.malgn.global.exception.AppException;
import com.malgn.member.domain.MemberErrorCode;
import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void deleteMember(CustomUser user) {

        Member member = getMember(user);

        if (member.isDeleted()) {
            throw new AppException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        member.delete();
    }

    private Member getMember(CustomUser user) {
        return memberRepository.findByUsername(user.getUsername()).orElseThrow(
            () -> new AppException(MemberErrorCode.MEMBER_NOT_FOUND)
        );
    }
}
