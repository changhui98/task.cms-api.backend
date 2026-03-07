package com.malgn.global.security;

import com.malgn.global.configure.CustomUser;
import com.malgn.global.exception.AppException;
import com.malgn.global.exception.ErrorResponse;
import com.malgn.global.security.jwt.JwtTokenProvider;
import com.malgn.member.domain.MemberErrorCode;
import com.malgn.member.domain.entity.MemberRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String LOGIN_PROCESS_URL = "/api/v1/auth/sign-in";

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl(LOGIN_PROCESS_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        try {
            FromLoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), FromLoginRequest.class);

            UsernamePasswordAuthenticationToken authRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new AppException(MemberErrorCode.MEMBER_UNAUTHORIZED);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult) {

        Long id = ((CustomUser) authResult.getPrincipal()).getId();
        String username = ((CustomUser) authResult.getPrincipal()).getUsername();
        MemberRole role = ((CustomUser) authResult.getPrincipal()).getRole();

        String token = jwtTokenProvider.createToken(id, username, role);
        response.addHeader(AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {

        response.setStatus(MemberErrorCode.INVALID_CREDENTIALS.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = ErrorResponse.from(MemberErrorCode.INVALID_CREDENTIALS);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);

    }
}
