package com.devopsbuddy.fpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthProvider {

    List<Month> months = new ArrayList<>();

    public MonthProvider() {
        months.add(new Month("August", Calendar.AUGUST));
        months.add(new Month("Sepetember", Calendar.SEPTEMBER));
        months.add(new Month("October", Calendar.OCTOBER));
        months.add(new Month("November", Calendar.NOVEMBER));
        months.add(new Month("December", Calendar.DECEMBER));
    }

    public List<Month> getList() {
        return months;
    }

}
