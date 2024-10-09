package com.example.song_request2;

import com.example.song_request2.API.ApiService;
import com.example.song_request2.Liked_Song.LikedSongRepository;
import com.example.song_request2.Liked_Song.LikedSongs;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.json.JSONException;

@SpringBootApplication
public class SongRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongRequestApplication.class, args);
    }

    // Example usage: Searching a track from Deezer API at the start of the application
    @Bean
    CommandLineRunner initDeezerApiAndLikedSongs(LikedSongRepository likedSongRepository) {
        return args -> {
            ApiService deezerApiService = new ApiService();
            try {
                // Retrieve the trackID for the dummy data by searching for the track "Levitating"
                long trackID = deezerApiService.searchTrack("Levitating");  // Now using long for trackID

                // Fetch and print the track details using the trackID
                deezerApiService.getTrackDetailsById(trackID);

                // Creating dummy LikedSongs entities with the dynamic trackID
                LikedSongs song1 = new LikedSongs(trackID, "Levitating", "Pop", 1); // Dynamic trackID
                LikedSongs song2 = new LikedSongs(987654321L, "Blinding Lights", "Synthwave", 2);
                LikedSongs song3 = new LikedSongs(123987456L, "Shape of You", "Pop", 3);

                // Saving LikedSongs entities to the database, using the dynamic trackID for song1
                likedSongRepository.save(song1);
                likedSongRepository.save(song2);
                likedSongRepository.save(song3);

                System.out.println("Dummy data saved with dynamic trackID: " + trackID);

            } catch (JSONException e) {
                System.err.println("Error searching for track: " + e.getMessage());
            }
        };
    }
}
