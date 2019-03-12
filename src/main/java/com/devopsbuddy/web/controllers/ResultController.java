package com.devopsbuddy.web.controllers;

import com.devopsbuddy.exceptions.FplResponseException;
import com.devopsbuddy.model.League;
import com.devopsbuddy.model.Month;
import com.devopsbuddy.model.Player;
import com.devopsbuddy.web.util.MonthProvider;
import com.devopsbuddy.web.util.UserInput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;

@Controller
public class ResultController {

    @ModelAttribute
    public void addMonthList(Model model) {
        MonthProvider monthProvider = new MonthProvider();
        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);
    }

    @PostMapping("/result")
    public ModelAndView redirect(@ModelAttribute UserInput userInput, ModelMap model) {
        model.addAttribute("id", userInput.getId());
        model.addAttribute("month", userInput.getMonth());
        return new ModelAndView("redirect:/{id}/{month}", model);
    }

    @RequestMapping("{id}/{month}")
    public String displayResults(@PathVariable("id") String id, @PathVariable("month") String month,
                                 @ModelAttribute UserInput userInput, Model model) {
        if (month.equals("current")) {
            month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
            userInput.setMonth(month);
        }

        int monthValue;
        monthValue = Integer.parseInt(month);
        if (monthValue < 1 || monthValue > 12 || monthValue == 6 || monthValue == 7) {
            return "error";
        }

        userInput.setId(Integer.parseInt(id));
        try {
            League league = new League();
            List<Player> players = league.getOrderedList(id, Integer.parseInt(month));
            model.addAttribute("players", players);
        } catch (FplResponseException e) {
            return "team-not-found";
        }
        return "result";
    }
}
