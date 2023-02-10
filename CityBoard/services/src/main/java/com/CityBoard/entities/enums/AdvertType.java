package com.CityBoard.entities.enums;

public enum AdvertType {
    RENT("Аренда"),
    SALE("Продажа");

    private final String displayValue;

    private AdvertType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
