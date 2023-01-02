package com.CityBoard.common.dto.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RolesRepo{
    ROLE_USER(0),
    ROLE_MOD(1),
    ROLE_ADMIN(2);

    private final Integer value;

    private RolesRepo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
