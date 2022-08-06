package com.CityBoard.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    USER, MOD, ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
