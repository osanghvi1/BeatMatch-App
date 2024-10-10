package com.example.song_request2.API;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ApiService {

    // Deezer API base URL
    private static final String API_BASE_URL = "https://api.deezer.com";

    // Method to search for a track by name and return the track ID of the first result
    public long searchTrack(String trackName) throws JSONException {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Construct the full URL for the API request
        String searchUrl = API_BASE_URL + "/search/track?q=" + trackName;

        // Make a GET request to the Deezer API
        ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);

        // Parse the response JSON and extract the first track ID (songID)
        JSONObject responseJson = new JSONObject(response.getBody());
        JSONArray dataArray = responseJson.getJSONArray("data");

        if (dataArray.length() > 0) {
            // Get the first track from the search results
            JSONObject track = dataArray.getJSONObject(0);
            return track.getLong("id");  // Change to long to handle large numbers
        } else {
            throw new JSONException("No tracks found for the search query.");
        }
    }

    // Method to fetch track details by trackID (songID)
    public JSONObject getTrackDetailsById(long trackID) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String trackDetailsUrl = API_BASE_URL + "/track/" + trackID;

        // Make a GET request to fetch track details
        ResponseEntity<String> trackResponse = restTemplate.getForEntity(trackDetailsUrl, String.class);

        // Parse and return the track details as a JSON object
        return new JSONObject(trackResponse.getBody());
    }
}
