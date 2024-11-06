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
@EntityScan(basePackages = {"database.GenrePreferences", "database.com.example.forgotpassword", "database.User", "database.LikedSongs", "database.DislikedSongs","database.Friends", "database.SimilarGenres"})
  // Ensure entity scanning
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    // Uncomment this section if you need to add dummy users and laptops
    /*
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, LaptopRepository laptopRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com");
            User user2 = new User("Jane", "jane@somemail.com");
            User user3 = new User("Justin", "justin@somemail.com");
            User user4 = new User("Lawson", "lport@iastate.edu");

            Laptop laptop1 = new Laptop(2.5, 4, 8, "Lenovo", 300);
            Laptop laptop2 = new Laptop(4.1, 8, 16, "Hp", 800);
            Laptop laptop3 = new Laptop(3.5, 32, 32, "Dell", 2300);

            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user4.setLaptop(laptop3);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user4);
        };
    }
    */
}
