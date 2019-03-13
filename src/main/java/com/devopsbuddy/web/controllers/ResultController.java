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
        var monthProvider = new MonthProvider();
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
    public String displayResults(@PathVariable("id") String id,
                                 @PathVariable("month") String month,
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

        try {
            List<Player> players = getDataForAllPlayersInLeague(id, Integer.parseInt(month));
            model.addAttribute("players", players);
            userInput.setId(Integer.parseInt(id));
        } catch (FplResponseException e) {
            return "team-not-found";
        }
        return "result";
    }

    private List<Player> getDataForAllPlayersInLeague(String playerIdentifier, int month) throws FplResponseException {
        List<Player> players = new ArrayList<>();
        try {
            var initialPlayer = new PlayerData(playerIdentifier);
            var leagueData = new LeagueData(initialPlayer.getLeagueIdentifier());
            List<String> playerIds = leagueData.getPlayerIds();
            for (String id : playerIds) {
                PlayerData player = new PlayerData(id);
                String playerName = player.getName();
                String teamName = player.getTeam();
                int total = player.getScore(getMonthFromValue(month));
                players.add(new Player(playerName, teamName, total));
            }
        } catch (IOException exception) {
            throw new FplResponseException("Unable to get data from FPL API", exception);
        }
        var league = new League();
        return league.sortByScore(players);
    }


    private GameMonth getMonthFromValue(int value) {
        GameMonth gameweekMonth;
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
            default:
                gameweekMonth = GameMonth.SEPTEMBER;
        }
        return gameweekMonth;
    }
}
