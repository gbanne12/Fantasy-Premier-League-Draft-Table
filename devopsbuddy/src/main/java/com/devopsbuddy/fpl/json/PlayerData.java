package com.devopsbuddy.fpl.json;

import com.devopsbuddy.fpl.GameweekMonth;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    private JSONObject playerData;
    private String id;

    public PlayerData(String id) throws IOException {
        this.id = id;
        playerData = new JsonRequester().get("https://draft.premierleague.com/api/entry/" + id + "/history");
    }

   public String getLeagueIdentifier() {
        JSONObject json = (JSONObject) (playerData).get("entry");
        return ((JSONArray) json.get("league_set")).get(0).toString();
    }

    public int getScore (GameweekMonth month) throws IOException {
        JSONArray scores = (JSONArray) (playerData).get("history");
        String initialScore = ((JSONObject) scores.get(month.getStartWeek() - 1))
                .get("total_points")
                .toString();

        int finalWeek = month.getEndWeek();
        if (scores.length() < finalWeek) {
            finalWeek = scores.length();
        }

        //////////////////////////////////////////////
       /* JSONObject currentWeek = new JsonRequester().get("https://draft.premierleague.com/api/entry/" + id
                + "/event/" + Integer.toString(scores.length() - 1));
        JSONArray selectionsJson = currentWeek.getJSONArray("picks");

        List<String> selections = new ArrayList<>();
        for (int i = 0; i < selectionsJson.length(); i++) {
            selections.add(((JSONObject) selectionsJson.get(i)).get("element").toString());
        }

        JSONObject liveData = new JsonRequester().get("https://draft.premierleague.com/api/event/"
                + Integer.toString(scores.length() - 1) + "/live";*/


        /////////////////////////////////////////
        String endScore = ((JSONObject) scores.get(finalWeek- 1))
                .get("total_points")
                .toString();
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
