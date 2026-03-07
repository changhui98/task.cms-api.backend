package com.malgn.global.security;

public record FromLoginRequest(
    String username,
    String password
) {

}
