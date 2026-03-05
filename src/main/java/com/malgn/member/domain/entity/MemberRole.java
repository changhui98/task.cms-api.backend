package com.malgn.member.domain.entity;

public enum MemberRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    MemberRole(String authority) {
        this.authority = authority;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public String getAuthority() {
        return authority;
    }
}
