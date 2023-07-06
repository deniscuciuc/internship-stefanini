package com.stefanini.pizzariabackend.domain.enums;

public enum OrderStatus {
    PENDING_CONFIRMATION,
    IS_BEING_PREPARED,
    ON_THE_WAY,
    DELIVERED,
    PAID;

    public static boolean contains(String statusToFound) {
        statusToFound.toUpperCase();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.toString().equals(statusToFound)) {
                return true;
            }
        }
        return false;
    }
}