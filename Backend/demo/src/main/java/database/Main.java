package database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "database")  // Enable JPA repositories in the 'database' package
@EntityScan(basePackages = "database")  // Scan all entities in the 'database' package and its sub-packages
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
