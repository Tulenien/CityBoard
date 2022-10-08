package com.CityBoard.models.enums;

public enum AdvertStatus {
    VISIBLE("В поиске"),
    HIDDEN("Скрыто"),
    DELETED("Удалено");

    private final String displayValue;

    private AdvertStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
