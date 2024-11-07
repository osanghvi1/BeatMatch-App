package database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Main application class for the Spring Boot application.
 *
 * This class serves as the entry point for the application and consolidates
 * configuration settings from the previous two classes.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"database.GroupChat"})
@EnableJpaRepositories(basePackages = "database")  // Enable JPA repositories in the 'database' package
@EntityScan(basePackages = {"database.GenrePreferences", "database.com.example.forgotpassword", "database.User", "database.LikedSongs", "database.GroupChat"})  // Ensure entity scanning
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}
