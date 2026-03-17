package com.malgn.member.presentation.controller;

import com.malgn.global.configure.CustomUser;
import com.malgn.member.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @AuthenticationPrincipal CustomUser user
    ) {
        memberService.deleteMember(user);
        return ResponseEntity.noContent().build();
    }


}
