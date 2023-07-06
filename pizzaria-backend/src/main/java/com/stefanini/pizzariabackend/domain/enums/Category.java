package com.stefanini.pizzariabackend.domain.enums;

public enum Category {
    PIZZA,
    BREAKFAST,
    SNACKS,
    SALADS,
    SOUPS,
    MAIN_DISHES,
    DESSERT,
    BEVERAGES,
    SUPPLEMENTS;

    public static boolean contains(String categoryToFound) {
        categoryToFound.toUpperCase();
        for (Category category : Category.values()) {
            if (category.toString().equals(categoryToFound)) {
                return true;
            }
        }
        return false;
    }
}
