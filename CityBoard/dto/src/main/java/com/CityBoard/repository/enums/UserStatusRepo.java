package com.CityBoard.repository.enums;

public enum UserStatusRepo {
    ACTIVE(0),
    BANNED(1),
    DELETED(2);

    private final Integer value;

    private UserStatusRepo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
