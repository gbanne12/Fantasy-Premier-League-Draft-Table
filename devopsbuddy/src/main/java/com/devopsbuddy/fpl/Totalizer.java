package com.devopsbuddy.fpl;



import com.devopsbuddy.fpl.json.LeagueData;
import com.devopsbuddy.fpl.json.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class Totalizer {

    public List<Player> getLeagueTable(String playerIdentifier) throws Exception {
        PlayerData initialPlayer = new PlayerData(playerIdentifier);
        LeagueData league = new LeagueData(initialPlayer.getLeagueIdentifier());

        List<String> playerIds = league.getPlayerIds();
        List<Player> players = new ArrayList<>();
        for (String id : playerIds) {
            PlayerData player = new PlayerData(id);
            players.add(new Player(player.getName(), player.getTeam(), player.getScore()));
        }
       return players;
    }
}