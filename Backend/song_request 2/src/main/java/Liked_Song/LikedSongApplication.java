package Liked_Song;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LikedSongApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikedSongApplication.class, args);
    }

    @Bean
    CommandLineRunner initLikedSongs(LikedSongRepository likedSongRepository) {
        return args -> {
            // Creating dummy LikedSongs entities
            LikedSongs song1 = new LikedSongs(1, "Shape of You", "Pop");
            LikedSongs song2 = new LikedSongs(2, "Blinding Lights", "Synthwave");
            LikedSongs song3 = new LikedSongs(3, "Levitating", "Disco Pop");

            // Saving LikedSongs entities
            likedSongRepository.save(song1);
            likedSongRepository.save(song2);
            likedSongRepository.save(song3);
        };
    }
}
