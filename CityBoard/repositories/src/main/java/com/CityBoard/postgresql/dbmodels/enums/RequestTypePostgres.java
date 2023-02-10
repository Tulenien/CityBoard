package com.CityBoard.postgresql.dbmodels.enums;

public enum RequestTypePostgres {
    RENT(0), SALE(1), VIEWING(2);

    private final Integer value;

    private RequestTypePostgres(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
