package com.malgn.member.presentation.controller;

import com.malgn.member.application.service.AuthService;
import com.malgn.member.presentation.dto.request.MemberCreateRequest;
import com.malgn.member.presentation.dto.response.MemberCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(
        summary = "회원 가입",
        description = "회원 가입을 진행합니다."
    )
    @PostMapping("/sign-up")
    public ResponseEntity<MemberCreateResponse> signUp(
        @RequestBody @Valid MemberCreateRequest request
    ) {
        MemberCreateResponse res = authService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

}
