package com.CityBoard.postgresql.dbmodels.enums;

public enum AdvertTypePostgres {
    RENT(0),
    SALE(1);

    private final Integer value;

    private AdvertTypePostgres(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
