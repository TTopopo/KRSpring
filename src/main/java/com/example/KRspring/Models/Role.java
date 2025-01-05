package com.example.KRspring.Models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CUSTOMER, ROLE_FOREMAN;

    @Override
    public String getAuthority() {
        return name();
    }
}

