package com.devopsbuddy.web.controllers;

import com.devopsbuddy.fpl.Player;
import com.devopsbuddy.fpl.Totalizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String displayPlayerTotals(Model model) throws Exception {
        Totalizer totalizer = new Totalizer();
        List<Player> players = totalizer.getLeagueTable("235052");
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());

        model.addAttribute("players", players);
        return "index";
    }

}
