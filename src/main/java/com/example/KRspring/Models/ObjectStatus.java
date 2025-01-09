package com.example.KRspring.Models;

public enum ObjectStatus {
    NOT_STARTED("Не начат"),
    IN_PROGRESS("В работе"),
    IN_REVIEW("На проверке"),
    COMPLETED("Завершённый");

    private final String displayName;

    ObjectStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
