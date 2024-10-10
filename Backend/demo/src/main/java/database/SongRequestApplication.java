package com.example.song_request2;

import com.example.song_request2.API.ApiService;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SongRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongRequestApplication.class, args);
        try {
            // Initialize ApiService
            ApiService apiService = new ApiService();

            // Use a predefined songID to get track details
            long songID = 556140562; // Example Deezer songID

            // Fetch and print track details using the songID
            JSONObject trackDetails = apiService.getTrackDetailsById(songID);

            // Extract and print the song name from track details
            String songName = trackDetails.getString("title");
            System.out.println("Song Name: " + songName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
