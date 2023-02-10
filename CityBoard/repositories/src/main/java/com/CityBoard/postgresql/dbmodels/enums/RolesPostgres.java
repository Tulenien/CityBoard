package com.CityBoard.postgresql.dbmodels.enums;

public enum RolesPostgres {
    ROLE_USER(0),
    ROLE_MOD(1),
    ROLE_ADMIN(2);

    private final Integer value;

    private RolesPostgres(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
