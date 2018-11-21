package com.devopsbuddy.fpl;

public enum Month {

    AUGUST(1, 3),
    SEPTEMBER(3, 7),
    OCTOBER(7, 10),
    NOVEMBER(10, 14),
    DECEMBER(14, 20);

    private final int startWeek;
    private final int endWeek;


    Month(int startWeek, int endWeek) {
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
