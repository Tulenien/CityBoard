package com.CityBoard.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_USER("ROLE_USER"),
    ROLE_MOD("ROLE_MOD"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String displayValue;

    private Roles(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
