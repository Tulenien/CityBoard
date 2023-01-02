package com.CityBoard.common.dto.enums;

public enum RequestStatusRepo {
    PENDING(0), REJECTED(1), ACCEPTED(2), DELETED(3);
    private final Integer value;

    private RequestStatusRepo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
