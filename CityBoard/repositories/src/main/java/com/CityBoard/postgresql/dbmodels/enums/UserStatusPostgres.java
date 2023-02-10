package com.CityBoard.postgresql.dbmodels.enums;

public enum UserStatusPostgres {
    ACTIVE(0),
    BANNED(1),
    DELETED(2);

    private final Integer value;

    private UserStatusPostgres(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
