package org.dcuciuc.mark.enums;

public enum MarkType {
    ABSENT(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final Integer mark;

    MarkType(Integer mark) {
        this.mark = mark;
    }

    public Integer getIntegerValueOfMark() {
        return mark;
    }
}
