package com.CityBoard.repository.enums;

public enum AdvertTypeRepo {
    RENT(0),
    SALE(1);

    private final Integer value;

    private AdvertTypeRepo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
