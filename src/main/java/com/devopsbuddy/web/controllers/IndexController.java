package com.devopsbuddy.web.controllers;

import com.devopsbuddy.model.Month;
import com.devopsbuddy.web.util.MonthProvider;
import com.devopsbuddy.web.util.UserInput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Calendar;
import java.util.List;

@Controller
public class IndexController {

    @ModelAttribute
    public void addMonthList(Model model) {
        MonthProvider monthProvider = new MonthProvider();
        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("userInput", new UserInput());
        return "index";
    }
}
