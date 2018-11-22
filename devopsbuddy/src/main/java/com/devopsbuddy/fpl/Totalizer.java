package com.devopsbuddy.fpl;


import com.devopsbuddy.exceptions.FplResponseException;
import com.devopsbuddy.fpl.json.LeagueData;
import com.devopsbuddy.fpl.json.PlayerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Totalizer {

    public List<Player> getLeagueTable(String playerIdentifier, Month month) throws FplResponseException {
        List<Player> players = new ArrayList<>();
        try {
            PlayerData initialPlayer = new PlayerData(playerIdentifier);
            LeagueData league = new LeagueData(initialPlayer.getLeagueIdentifier());

            List<String> playerIds = league.getPlayerIds();
            for (String id : playerIds) {
                PlayerData player = new PlayerData(id);
                players.add(new Player(player.getName(), player.getTeam(), player.getScore(month)));
            }

        } catch (IOException exception) {
            throw new FplResponseException("Unable to get data from the draft FPL API", exception);
        }
        return players;
    }
}