package com.example.forgotpassword;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ForgotPasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForgotPasswordApplication.class, args);
    }

    @Bean
    CommandLineRunner initForgotPassword(ForgotPasswordRepository forgotPasswordRepository) {
        return args -> {
            // Creating dummy ForgotPassword entities
            ForgotPassword forgotPassword1 = new ForgotPassword("john_doe", "What is your pet's name?","Coupa", "What is your first car?","Acura");
            ForgotPassword forgotPassword2 = new ForgotPassword("jane_doe", "What city were you born in?","New York", "What is your favorite book?","Harry Potter");
            ForgotPassword forgotPassword3 = new ForgotPassword("justin_time", "What was your first job?","IT", "What is your favorite color?","Blue");

            // Saving ForgotPassword entities
            forgotPasswordRepository.save(forgotPassword1);
            forgotPasswordRepository.save(forgotPassword2);
            forgotPasswordRepository.save(forgotPassword3);
        };
    }
}
