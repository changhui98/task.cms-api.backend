package com.malgn.member.presentation.controller;

import com.malgn.member.application.service.AuthService;
import com.malgn.member.presentation.dto.request.MemberCreateRequest;
import com.malgn.member.presentation.dto.response.MemberCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberCreateResponse> signUp(
        @RequestBody MemberCreateRequest request
    ) {
        MemberCreateResponse res = authService.signUp(request);

        return ResponseEntity.ok(res);
    }

}
