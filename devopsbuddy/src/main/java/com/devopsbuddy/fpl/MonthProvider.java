package com.devopsbuddy.fpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthProvider {

    private List<Month> months;

    public MonthProvider() {
        months = new ArrayList<>();
        months.add(new Month("August", Calendar.AUGUST));
        months.add(new Month("September", Calendar.SEPTEMBER));
        months.add(new Month("October", Calendar.OCTOBER));
        months.add(new Month("November", Calendar.NOVEMBER));
        months.add(new Month("December", Calendar.DECEMBER));
        months.add(new Month("January", Calendar.JANUARY));
        months.add(new Month("February", Calendar.FEBRUARY));
        months.add(new Month("March", Calendar.MARCH));
        months.add(new Month("April", Calendar.APRIL));
        months.add(new Month("May", Calendar.MAY));
    }

    public List<Month> getList() {
        return months;
    }

}
