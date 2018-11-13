package com.devopsbuddy.fpl.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeagueData {

    private JSONObject leagueData;

    public LeagueData(String leagueId) throws IOException {
        leagueData = new JsonRequester().get("https://draft.premierleague.com/api" + "/league/" + leagueId + "/details");
    }

    public List<String> getPlayerIds() {
        JSONArray players = (JSONArray) leagueData.get("league_entries");
        List<String> playerIds = new ArrayList<>();
        for (int i = 0; i < players.length(); i++) {
            playerIds.add(((JSONObject) players.get(i)).get("entry_id").toString());
        }
        return playerIds;
    }
}
