package com.devopsbuddy;

import com.devopsbuddy.fpl.json.JsonRequester;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveScoreTests {

	@Test
	public void canGetLiveTotals() throws IOException {
		String id = "235052";
		JSONObject playerData = new JsonRequester().get("https://draft.premierleague.com/api/entry/" + id + "/history");
		JSONArray scores = (JSONArray) (playerData).get("history");

		JSONObject currentWeek = new JsonRequester().get("https://draft.premierleague.com/api/entry/" + id
				+ "/event/" + Integer.toString(scores.length()));
		JSONArray selectionsJson = currentWeek.getJSONArray("picks");

		List<String> selections = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
		    boolean selected = ((JSONObject) selectionsJson.get(i)).getInt("position") < 12;
		    if (selected) {
                selections.add(((JSONObject) selectionsJson.get(i)).get("element").toString());
            }
		}

		JSONObject liveData = new JsonRequester().get("https://draft.premierleague.com/api/event/"
				+ Integer.toString(scores.length()) + "/live");
		JSONObject elements = (JSONObject) liveData.get("elements");

		int total = 0;
        for (int i = 0; i < selections.size(); i++) {
            JSONObject currentSelection = (JSONObject) elements.get(selections.get(i));
            JSONObject currentStats =  (JSONObject) currentSelection.get("stats");
            int points =  ((JSONObject) currentSelection.get("stats")).getInt("total_points");
            total = total +  points;
        }

		System.out.println("TOTAL: = " + total);
	}

}
