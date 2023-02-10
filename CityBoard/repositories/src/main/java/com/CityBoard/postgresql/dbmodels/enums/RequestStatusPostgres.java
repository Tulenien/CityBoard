package com.CityBoard.postgresql.dbmodels.enums;

public enum RequestStatusPostgres {
    PENDING(0), REJECTED(1), ACCEPTED(2), DELETED(3);
    private final Integer value;

    private RequestStatusPostgres(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
