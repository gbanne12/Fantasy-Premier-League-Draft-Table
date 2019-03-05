package com.devopsbuddy.fpl.json;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JsonRequester {

    public JSONObject get(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        StringBuilder responseString = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            responseString.append(line);
        }
        return new JSONObject(responseString.toString());
    }

}
