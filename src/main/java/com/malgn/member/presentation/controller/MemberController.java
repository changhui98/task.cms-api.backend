package com.malgn.member.presentation.controller;

import com.malgn.global.configure.CustomUser;
import com.malgn.member.application.service.MemberService;
import com.malgn.member.presentation.dto.response.MemberListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<MemberListResponse>> getMembers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<MemberListResponse> res = memberService.getMembers(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @AuthenticationPrincipal CustomUser user
    ) {
        memberService.deleteMember(user);
        return ResponseEntity.noContent().build();
    }


}
