package com.devopsbuddy.model;


import java.util.List;

public class League {

    public List<Player> getOrderedList(List<Player> players) {
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        return players;
    }
}