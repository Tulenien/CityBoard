package com.CityBoard.postgresql.dbmodels.enums;

public enum AdvertStatusPostgres {
    VISIBLE(0),
    HIDDEN(1),
    DELETED(2);

    private final Integer value;

    private AdvertStatusPostgres(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
