package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.GameweekMonth;
import com.devopsbuddy.fpl.Player;
import com.devopsbuddy.fpl.Team;
import com.devopsbuddy.fpl.League;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/")
    public String initialise(Model model) {
        model.addAttribute("team", new Team());
        return "index";
    }

    @PostMapping("/result")
    public String submit(@ModelAttribute Team team, Model model) throws IOException {
        String teamId = Integer.toString(team.getId());
        GameweekMonth gameweekMonth = getMonthFromValue(team.getMonth());
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
