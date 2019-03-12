package com.devopsbuddy.model;

public enum GameMonth {

    AUGUST(1, 3),
    SEPTEMBER(3, 7),
    OCTOBER(7, 10),
    NOVEMBER(10, 14),
    DECEMBER(14, 20),
    JANUARY(20, 24),
    FEBRUARY(24, 28),
    MARCH(28, 32),
    APRIL(32, 36),
    MAY(36, 38);

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
