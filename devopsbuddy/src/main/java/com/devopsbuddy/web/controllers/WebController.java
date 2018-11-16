package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.Player;
import com.devopsbuddy.fpl.Team;
import com.devopsbuddy.fpl.Totalizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
public class WebController {

    @Value("88888")
    private String teamId;

    @Value("10")
    private int monthId;

    @GetMapping("/")
    public String init(Model model) {
        model.addAttribute("team", new Team());
        return "index";
    }

    @PostMapping("/index")
    public String submit(@ModelAttribute Team team, Model model) throws IOException {
        teamId = Integer.toString(team.getId());
        Totalizer totalizer = new Totalizer();
        List<Player> players = totalizer.getLeagueTable(teamId, monthId);
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        model.addAttribute("players", players);

        return "result";
    }

    @PostMapping("/month")
    public String monthSelection(@ModelAttribute Team team, Model model) throws IOException {
        monthId = team.getMonth();
        Totalizer totalizer = new Totalizer();
        List<Player> players = totalizer.getLeagueTable(teamId, monthId);
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        model.addAttribute("players", players);

        return "month";
    }

}
