package com.devopsbuddy.fpl;

public enum GameweekMonth {

    AUGUST(1, 3),
    SEPTEMBER(3, 7),
    OCTOBER(7, 10),
    NOVEMBER(10, 14),
    DECEMBER(14, 20),
    JANUARY(14, 20),
    FEBRUARY(14, 20),
    MARCH(14, 20),
    APRIL(14, 20),
    MAY(14, 20);

    private final int startWeek;
    private final int endWeek;

    GameweekMonth(int startWeek, int endWeek) {
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
