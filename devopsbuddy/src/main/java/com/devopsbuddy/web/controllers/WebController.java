package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.spi.CalendarDataProvider;

@Controller
public class WebController {

    MonthProvider monthProvider = new MonthProvider();

    @GetMapping("/")
    public String initialise(Model model) {
        model.addAttribute("userInput", new UserInput());

        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        model.addAttribute("currentMonth", currentMonth);

        return "index";
    }

    @PostMapping("/result")
    public String submit(@ModelAttribute UserInput userInput, Model model) throws IOException {
        List<Month> months = monthProvider.getList();
        model.addAttribute("months", months);

        String teamId = Integer.toString(userInput.getId());
        GameweekMonth gameweekMonth = getMonthFromValue(userInput.getMonth());
        League league = new League();
        List<Player> players = league.getData(teamId, gameweekMonth);
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        model.addAttribute("players", players);

        return "result";
    }

    private GameweekMonth getMonthFromValue(int value) {
        GameweekMonth gameweekMonth = GameweekMonth.NOVEMBER;
        switch (value) {
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
