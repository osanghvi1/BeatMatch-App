package com.example.forgotpassword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    // Custom method to find ForgotPassword entity by username
    ForgotPassword findByUsername(String username);
}
