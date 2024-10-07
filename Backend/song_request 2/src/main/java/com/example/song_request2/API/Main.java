package com.example.song_request2.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        // Example usage
        ApiService deezerApiService = new ApiService();
        deezerApiService.searchTrack("Levitating");  // Replace with any track name
    }
}
