package com.devopsbuddy.model;

public class Player {

    private String teamName;
    private String playerName;
    public int total;

    public Player(String playerName, String teamName, int total) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.total = total;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
