package com.CityBoard.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_USER("Пользователь"),
    ROLE_MOD("Модератор"),
    ROLE_ADMIN("Администратор");

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
