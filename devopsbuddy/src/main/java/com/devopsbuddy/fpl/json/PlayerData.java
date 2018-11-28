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

    public int getScore(GameweekMonth month) throws IOException {
        JSONArray scores = (JSONArray) (playerData).get("history");
        
        if (month.getStartWeek() > scores.length()) {
            return 0;
        }

        int initialScore = Integer.parseInt(
                ((JSONObject) scores.get(month.getStartWeek() - 1)).get("total_points").toString());

        int endScore;
        int finalWeek = month.getEndWeek();
        if (finalWeek >= scores.length()) {
            finalWeek = scores.length();
            List<String> selections = getCurrentSelections(finalWeek);
            int inPlayScore = getLiveGameWeekScore(selections);
            endScore = inPlayScore + Integer.parseInt(
                    ((JSONObject) scores.get(finalWeek - 2))
                            .get("total_points")
                            .toString());

        } else {
            endScore = Integer.parseInt(
                    ((JSONObject) scores.get(finalWeek - 1))
                            .get("total_points")
                            .toString());
        }

        return endScore - initialScore;
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

    private List<String> getCurrentSelections(int week) throws IOException {
        JSONObject currentWeek = new JsonRequester().get("https://draft.premierleague.com/api/entry/"
                + id + "/event/" + week);
        JSONArray currentTeam = currentWeek.getJSONArray("picks");

        List<String> selectionIds = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            selectionIds.add(((JSONObject) currentTeam.get(i)).get("element").toString());
        }
        return selectionIds;
    }

    private int getLiveGameWeekScore(List<String> selectionIds) throws IOException {
        int week = ((JSONArray) (playerData).get("history")).length();
        JSONObject liveData = new JsonRequester().get("https://draft.premierleague.com/api/event/" + week + "/live");
        JSONObject elements = (JSONObject) liveData.get("elements");

        int total = 0;
        for (String id : selectionIds) {
            JSONObject currentSelection = (JSONObject) elements.get(id);
            int points = ((JSONObject) currentSelection.get("stats")).getInt("total_points");
            total = total + points;
        }

        return total;
    }
}
