package com.example.forgotpassword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    ForgotPassword findByUsername(String username);
}
