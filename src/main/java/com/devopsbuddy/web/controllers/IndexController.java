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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.ArrayList;
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

        try {
            List<Player> players = getDataForAllPlayersInLeague("449006", currentMonth);
            model.addAttribute("players", players);
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
            System.out.println("'''''''''''''''''''");
            for (String id : playerIds) {
                PlayerData player = new PlayerData(id);
                String playerName = player.getName();
                String teamName = player.getTeam();
                int total = player.getScore(getMonthFromValue(month));
                System.out.println("Month = " + getMonthFromValue(month));
                System.out.println(playerName + " - " + total);
                players.add(new Player(playerName, teamName, total));
            }
            System.out.println("'''''''''''''''''''");
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
