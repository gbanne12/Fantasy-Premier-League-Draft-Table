package com.devopsbuddy.web.controllers;

import com.devopsbuddy.exceptions.FplResponseException;
import com.devopsbuddy.fpl.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;

@Controller
public class WebController {

    @ModelAttribute
    public void addMonthList(Model model) {
        MonthProvider monthProvider = new MonthProvider();
        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("userInput", new UserInput());
        return "index";
    }

    @GetMapping("how-to")
    public String helpPage(Model model) {
        return "howto";
    }

    @PostMapping("/result")
    public ModelAndView redirect(@ModelAttribute UserInput userInput, ModelMap model) {
        model.addAttribute("id", userInput.getId());
        model.addAttribute("month", userInput.getMonth());
        return new ModelAndView("redirect:/{id}/{month}", model);
    }

    @RequestMapping("{id}/{month}")
    public String displayResults(@PathVariable("id") String id,
                                 @PathVariable("month") String month,
                                 @ModelAttribute UserInput userInput,
                                 Model model) {
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
