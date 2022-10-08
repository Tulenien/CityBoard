package com.CityBoard.services.dsrouting;

import com.CityBoard.models.enums.Roles;

public class DBContextHolder {
    private static final ThreadLocal<Roles> contextHolder = new ThreadLocal<>();

    public static Roles getCurrentConnect() {
        return contextHolder.get();
    }

    public static void setCurrentConnect(Roles connectType) {
        contextHolder.set(connectType);
    }

    public static void clear() {
        contextHolder.remove();
    }
}