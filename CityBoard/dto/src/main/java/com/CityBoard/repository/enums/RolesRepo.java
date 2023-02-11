package com.CityBoard.repository.enums;

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
