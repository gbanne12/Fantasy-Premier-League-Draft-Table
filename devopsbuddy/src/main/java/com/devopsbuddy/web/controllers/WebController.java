package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.Month;
import com.devopsbuddy.fpl.Player;
import com.devopsbuddy.fpl.Team;
import com.devopsbuddy.fpl.League;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

    @Value("77777")
    private int teamId;

    @Value("11")
    private int monthId;

    @GetMapping("/")
    public String initialise(Model model) {
        model.addAttribute("team", new Team());
        return "index";
    }

    @PostMapping("/result")
    public String submit(@ModelAttribute Team team, Model model) throws IOException {
        String teamId = Integer.toString(team.getId());
        Month month = getMonthFromValue(team.getMonth());
        League league = new League();
        List<Player> players = league.getData(teamId, month);
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());

        model.addAttribute("players", players);
        return "result";
    }

    private Month getMonthFromValue(int value) {
        Month month = Month.NOVEMBER;
        switch (value) {
            case 8:
                month = Month.AUGUST;
                break;
            case 9:
                month = Month.SEPTEMBER;
                break;
            case 10:
                month = Month.OCTOBER;
                break;
            case 11:
                month = Month.NOVEMBER;
                break;
        }
        return month;
    }
}
