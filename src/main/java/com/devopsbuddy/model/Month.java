package com.devopsbuddy.model;

import java.util.Calendar;

public class Month {

    private String name;
    private int value;

    public Month(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public boolean isSelected(int month) {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        boolean result = month == currentMonth;
        return result;
    }
}
