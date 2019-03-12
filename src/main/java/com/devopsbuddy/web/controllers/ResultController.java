package com.devopsbuddy.web.controllers;

import com.devopsbuddy.exceptions.FplResponseException;
import com.devopsbuddy.fpl.json.LeagueData;
import com.devopsbuddy.fpl.json.PlayerData;
import com.devopsbuddy.model.GameMonth;
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

import java.io.IOException;
import java.util.ArrayList;
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
            List<Player> players = getLeagueTable(id, monthValue);
            League league = new League();
            List<Player> ordered = league.getOrderedList(players);
            //List<Player> players = league.getOrderedList(id, Integer.parseInt(month));
            model.addAttribute("players", ordered);
        } catch (FplResponseException e) {
            return "team-not-found";
        }
        return "result";
    }

    private List<Player> getLeagueTable(String playerIdentifier, int month) throws FplResponseException {
        List<Player> players = new ArrayList<>();
        try {
            PlayerData initialPlayer = new PlayerData(playerIdentifier);
            LeagueData leagueData = new LeagueData(initialPlayer.getLeagueIdentifier());

            List<String> playerIds = leagueData.getPlayerIds();
            for (String id : playerIds) {
                PlayerData player = new PlayerData(id);
                players.add(new Player(player.getName(), player.getTeam(), player.getScore(getMonthFromValue(month))));
            }

        } catch (IOException exception) {
            throw new FplResponseException("Unable to get data from the draft FPL API", exception);
        }
        return players;
    }

    private GameMonth getMonthFromValue(int value) {
        GameMonth gameweekMonth = GameMonth.NOVEMBER;
        switch (value) {
            case 1:
                gameweekMonth = GameMonth.JANUARY;
                break;
            case 2:
                gameweekMonth = GameMonth.FEBRUARY;
                break;
            case 3:
                gameweekMonth = GameMonth.MARCH;
                break;
            case 4:
                gameweekMonth = GameMonth.APRIL;
                break;
            case 5:
                gameweekMonth = GameMonth.MAY;
                break;
            case 8:
                gameweekMonth = GameMonth.AUGUST;
                break;
            case 9:
                gameweekMonth = GameMonth.SEPTEMBER;
                break;
            case 10:
                gameweekMonth = GameMonth.OCTOBER;
                break;
            case 11:
                gameweekMonth = GameMonth.NOVEMBER;
                break;
            case 12:
                gameweekMonth = GameMonth.DECEMBER;
                break;
        }
        return gameweekMonth;
    }

}
