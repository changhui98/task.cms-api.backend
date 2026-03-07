package com.malgn.member.application.service;

import com.malgn.global.exception.AppException;
import com.malgn.member.domain.MemberErrorCode;
import com.malgn.member.domain.entity.Member;
import com.malgn.member.domain.repository.MemberRepository;
import com.malgn.member.presentation.dto.request.MemberCreateRequest;
import com.malgn.member.presentation.dto.response.MemberCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberCreateResponse signUp(MemberCreateRequest request) {

        validateDuplicateUsername(request.username());

        Member member = Member.of(
            request.username(),
            passwordEncoder.encode(request.password())
        );

        Member saveMember = memberRepository.save(member);

        return new MemberCreateResponse(
            saveMember.getId(),
            saveMember.getUsername(),
            saveMember.getCreatedDate()
        );
    }

    private void validateDuplicateUsername(String username) {

        if (memberRepository.existsByUsername(username)) {
            throw new AppException(MemberErrorCode.DUPLICATE_USERNAME);
        }
    }
}
