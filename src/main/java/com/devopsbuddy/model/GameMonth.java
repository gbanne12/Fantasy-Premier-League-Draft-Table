package com.devopsbuddy.model;

public enum GameMonth {

    AUGUST(1, 5),
    SEPTEMBER(6, 8),
    OCTOBER(9, 14),
    NOVEMBER(15, 16),
    DECEMBER(17, 18),
    JANUARY(19, 21),
    FEBRUARY(22, 25),
    MARCH(26, 28),
    APRIL(29, 34),
    MAY(35, 38);

    private final int startWeek;
    private final int endWeek;

    GameMonth(int startWeek, int endWeek) {
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public int getStartWeek() {
        return startWeek;
    }
}
