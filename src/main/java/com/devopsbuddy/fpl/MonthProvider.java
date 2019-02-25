package com.devopsbuddy.fpl;

import java.util.ArrayList;
import java.util.List;

public class MonthProvider {

    private List<Month> months;

    public MonthProvider() {
        months = new ArrayList<>();
        months.add(new Month("August", 8));
        months.add(new Month("September", 9));
        months.add(new Month("October", 10));
        months.add(new Month("November", 11));
        months.add(new Month("December", 12));
        months.add(new Month("January", 1));
        months.add(new Month("February", 2));
        months.add(new Month("March", 3));
        months.add(new Month("April", 4));
        months.add(new Month("May", 5));
    }

    public List<Month> getList() {
        return months;
    }

}
