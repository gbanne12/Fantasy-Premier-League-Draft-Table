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

    private GameMonth getMonthFromValue(int value) {
        GameMonth gameweekMonth = GameMonth.NOVEMBER;
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
            case 8:
                gameweekMonth = GameMonth.AUGUST;
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
        }
        return gameweekMonth;
    }

}