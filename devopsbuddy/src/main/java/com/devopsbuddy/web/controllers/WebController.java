package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.Month;
import com.devopsbuddy.fpl.Player;
import com.devopsbuddy.fpl.Team;
import com.devopsbuddy.fpl.Totalizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
public class WebController {

    @Value("77777")
    private int teamId;

    @Value("11")
    private int monthId;

    @GetMapping("/")
    public String init(Model model) {
        model.addAttribute("team", new Team());
        return "index";
    }

    @PostMapping("/result")
    public String submit(@ModelAttribute Team team, Model model) throws IOException {
        teamId = team.getId();
        monthId = team.getMonth();
        Totalizer totalizer = new Totalizer();
        List<Player> players = totalizer.getLeagueTable(Integer.toString(teamId), getMonth(monthId));
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        model.addAttribute("players", players);

        return "result";
    }

    private Month getMonth(int value) {
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
