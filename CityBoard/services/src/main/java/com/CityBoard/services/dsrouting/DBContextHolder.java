package com.CityBoard.services.dsrouting;

public class DBContextHolder {
    private static final ThreadLocal<DBNames> contextHolder = new ThreadLocal<>();

    public static DBNames getCurrentConnect() {
        return contextHolder.get();
    }

    public static void setCurrentConnect(DBNames connectType) {
        contextHolder.set(connectType);
    }

    public static void clear() {
        contextHolder.remove();
    }
}