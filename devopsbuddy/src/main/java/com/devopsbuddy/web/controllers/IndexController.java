package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.Player;
import com.devopsbuddy.fpl.Team;
import com.devopsbuddy.fpl.Totalizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public String displayPlayerTotals(Model model) {
        model.addAttribute("team", new Team());

        return "index";
    }

    @PostMapping("/index")
    public String greetingSubmit(@ModelAttribute Team team, Model model) throws IOException {
        Totalizer totalizer = new Totalizer();
        List<Player> players = totalizer.getLeagueTable(Integer.toString(team.getId()));
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        model.addAttribute("players", players);

        return "result";
    }

}
