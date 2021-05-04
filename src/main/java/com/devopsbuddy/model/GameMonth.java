package com.devopsbuddy.model;

public enum GameMonth {

    SEPTEMBER(1, 3),
    OCTOBER(4, 7),
    NOVEMBER(8, 10),
    DECEMBER(11, 16),
    JANUARY(17, 21),
    FEBRUARY(22, 26),
    MARCH(27, 29),
    APRIL(30, 34),
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
