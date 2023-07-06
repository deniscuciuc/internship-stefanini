package com.stefanini.pizzariabackend.domain.enums;

public enum ReviewMark {
    BAD(1),
    NOT_GOOD(2),
    GOOD(3),
    VERY_GOOD(4),
    EXCELLENT(5);

    private final int stars;

    ReviewMark(int stars) {
        this.stars = stars;
    }


    public int getStars() {
        return stars;
    }
}
