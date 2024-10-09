package database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"database.GenrePreferences","database.com.example.forgotpassword", "database.User"})  // Make sure entity scanning is included
public class ForgotPasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForgotPasswordApplication.class, args);
    }
}
