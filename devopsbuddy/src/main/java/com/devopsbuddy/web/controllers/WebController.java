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

    List<Month> months = new ArrayList<>();

    @GetMapping("/")
    public String initialise(Model model) {

        months.add(new Month("August", Calendar.AUGUST));
        months.add(new Month("Sepetember", Calendar.SEPTEMBER));
        months.add(new Month("October", Calendar.OCTOBER));
        months.add(new Month("November", Calendar.NOVEMBER));
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("months", months);
        model.addAttribute("userInput", new UserInput());
        return "index";
    }

    @PostMapping("/result")
    public String submit(@ModelAttribute UserInput userInput, Model model) throws IOException {
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
            case 8:
                gameweekMonth = GameweekMonth.AUGUST;
                break;
            case 9:
                gameweekMonth = GameweekMonth.SEPTEMBER;
                break;
            case 10:
                gameweekMonth = GameweekMonth.OCTOBER;
                break;
            case 11:
                gameweekMonth = GameweekMonth.NOVEMBER;
                break;
        }
        return gameweekMonth;
    }
}
