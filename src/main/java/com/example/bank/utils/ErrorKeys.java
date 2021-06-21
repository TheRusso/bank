package com.example.bank.utils;

public enum ErrorKeys {
    CANT_FIND_USER("cant_find_user"),
    BAD_REQUEST("bad_request"),
    LOW_BALANCE("low_balance");

    private final String key;

    ErrorKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
