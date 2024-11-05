package database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class for the Application
 */
@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = {"database.GenrePreferences", "database.com.example.forgotpassword", "database.User", "database.LikedSongs"})
  // Ensure entity scanning
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }
}
