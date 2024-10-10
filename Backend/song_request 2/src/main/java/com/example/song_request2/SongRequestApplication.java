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

}
