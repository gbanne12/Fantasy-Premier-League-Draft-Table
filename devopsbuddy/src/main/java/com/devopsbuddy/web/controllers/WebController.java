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

    private static UserInput userInput = new UserInput();
    private MonthProvider monthProvider = new MonthProvider();

    @GetMapping("/")
    public String initialise(Model model) {
        model.addAttribute("userInput", userInput);

        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        model.addAttribute("currentMonth", currentMonth);

        return "index";
    }

    @GetMapping("how-to")
    public String helpPage(Model model) {
        return "howto";
    }

    @PostMapping("/result")
    public ModelAndView redirect(@ModelAttribute UserInput userInput, ModelMap model) {
        model.addAttribute("teamIdentifier", userInput.getId());
        model.addAttribute("month", userInput.getMonth());
        model.addAttribute("userInput", userInput);

        return new ModelAndView("redirect:/{teamIdentifier}/{month}", model);
    }

    @RequestMapping("{teamIdentifier}/{month}")
    public String displayResults(@ModelAttribute UserInput userInput,
                                 @PathVariable("teamIdentifier") String team,
                                 @PathVariable("month") String month,
                                 Model model) {
        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);
        model.addAttribute("userInput", userInput);


        try {
            League league = new League();
            List<Player> players = league.getData(team, getMonthFromValue(Integer.parseInt(month)));
            players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
            model.addAttribute("players", players);

        } catch (FplResponseException e) {
            return "team-not-found";
        }

        return "result";
    }

    private GameweekMonth getMonthFromValue(int value) {
        GameweekMonth gameweekMonth = GameweekMonth.NOVEMBER;
        switch (value) {
            case 0:
                gameweekMonth = GameweekMonth.JANUARY;
                break;
            case 1:
                gameweekMonth = GameweekMonth.FEBRUARY;
                break;
            case 2:
                gameweekMonth = GameweekMonth.MARCH;
                break;
            case 3:
                gameweekMonth = GameweekMonth.APRIL;
                break;
            case 4:
                gameweekMonth = GameweekMonth.MAY;
                break;
            case 7:
                gameweekMonth = GameweekMonth.AUGUST;
                break;
            case 8:
                gameweekMonth = GameweekMonth.SEPTEMBER;
                break;
            case 9:
                gameweekMonth = GameweekMonth.OCTOBER;
                break;
            case 10:
                gameweekMonth = GameweekMonth.NOVEMBER;
                break;
            case 11:
                gameweekMonth = GameweekMonth.DECEMBER;
                break;
        }
        return gameweekMonth;
    }
}
