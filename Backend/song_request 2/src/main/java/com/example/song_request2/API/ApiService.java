package com.example.song_request2.API;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiService {

    // Deezer API base URL
    private static final String API_BASE_URL = "https://api.deezer.com";

    // Method to search for a track by name
    public void searchTrack(String trackName) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Construct the full URL for the API request
        String searchUrl = API_BASE_URL + "/search/track?q=" + trackName;

        // Make a GET request to the Deezer API
        ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);

        // Parse the response JSON and extract the track ID (songID)
        JSONObject responseJson = new JSONObject(response.getBody());
        JSONArray dataArray = responseJson.getJSONArray("data");

        // Iterate through the results and print the track IDs
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject track = dataArray.getJSONObject(i);
            int songId = track.getInt("id");
            String songTitle = track.getString("title");
            System.out.println("Track ID: " + songId + ", Title: " + songTitle);
        }
    }
}
