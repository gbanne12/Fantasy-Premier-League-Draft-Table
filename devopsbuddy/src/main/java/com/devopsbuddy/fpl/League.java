package com.devopsbuddy.fpl;


import com.devopsbuddy.exceptions.FplResponseException;
import com.devopsbuddy.fpl.json.LeagueData;
import com.devopsbuddy.fpl.json.PlayerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class League {

    public List<Player> getOrderedList(String playerIdentifier, int month) throws FplResponseException {
        List<Player> players = new ArrayList<>();
        try {
            PlayerData initialPlayer = new PlayerData(playerIdentifier);
            LeagueData leagueData = new LeagueData(initialPlayer.getLeagueIdentifier());

            List<String> playerIds = leagueData.getPlayerIds();
            for (String id : playerIds) {
                PlayerData player = new PlayerData(id);
                players.add(new Player(player.getName(), player.getTeam(), player.getScore(getMonthFromValue(month))));
            }

        } catch (IOException exception) {
            throw new FplResponseException("Unable to get data from the draft FPL API", exception);
        }
        players.sort((p1, p2) -> p2.getTotal() - p1.getTotal());
        return players;
    }

    private GameweekMonth getMonthFromValue(int value) {
        GameweekMonth gameweekMonth = GameweekMonth.NOVEMBER;
        switch (value) {
            case 0:
                gameweekMonth = GameweekMonth.JANUARY;
                break;
            case 1:
                gameweekMonth = GameweekMonth.FEBRUARY;
                break;
            case 2:
                gameweekMonth = GameweekMonth.MARCH;
                break;
            case 3:
                gameweekMonth = GameweekMonth.APRIL;
                break;
            case 4:
                gameweekMonth = GameweekMonth.MAY;
                break;
            case 7:
                gameweekMonth = GameweekMonth.AUGUST;
                break;
            case 8:
                gameweekMonth = GameweekMonth.SEPTEMBER;
                break;
            case 9:
                gameweekMonth = GameweekMonth.OCTOBER;
                break;
            case 10:
                gameweekMonth = GameweekMonth.NOVEMBER;
                break;
            case 11:
                gameweekMonth = GameweekMonth.DECEMBER;
                break;
        }
        return gameweekMonth;
    }

}