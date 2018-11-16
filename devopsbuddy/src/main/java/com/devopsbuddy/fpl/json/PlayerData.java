package com.devopsbuddy.fpl.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class PlayerData {

    private JSONObject playerData;

    public PlayerData(String id) throws IOException {
        playerData = new JsonRequester().get("https://draft.premierleague.com/api/entry/" + id + "/history");
    }

   public String getLeagueIdentifier() {
        JSONObject json = (JSONObject) (playerData).get("entry");
        return ((JSONArray) json.get("league_set"))
                .get(0)
                .toString();
    }

    public int getScore(int month) {
        int LAST_WEEK_OF_PREVIOUS_MONTH = 10;
        if (month == 9) {
            LAST_WEEK_OF_PREVIOUS_MONTH = 3;
        } else if (month == 10) {
            LAST_WEEK_OF_PREVIOUS_MONTH = 7;
        }

        JSONArray scores = (JSONArray) (playerData).get("history");
        String initialScore = ((JSONObject) scores.get(LAST_WEEK_OF_PREVIOUS_MONTH - 1))
                .get("total_points").toString();
        String endScore = ((JSONObject) scores.get(scores.length() - 1))
                .get("total_points").toString();
        return Integer.parseInt(endScore) - Integer.parseInt(initialScore);
    }

    public String getName() {
        JSONObject json = (JSONObject) (playerData).get("entry");
        return json.get("player_first_name").toString() + " "
                + json.get("player_last_name").toString();
    }

    public String getTeam() {
        JSONObject json = (JSONObject) (playerData).get("entry");
        return json.get("name").toString();
    }
}
