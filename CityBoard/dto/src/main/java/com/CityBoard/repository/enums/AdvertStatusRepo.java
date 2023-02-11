package com.CityBoard.repository.enums;

public enum AdvertStatusRepo {
    VISIBLE(0),
    HIDDEN(1),
    DELETED(2);

    private final Integer value;

    private AdvertStatusRepo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
