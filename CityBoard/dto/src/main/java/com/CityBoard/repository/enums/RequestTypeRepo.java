package com.CityBoard.repository.enums;

public enum RequestTypeRepo {
    RENT(0), SALE(1), VIEWING(2);

    private final Integer value;

    private RequestTypeRepo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
