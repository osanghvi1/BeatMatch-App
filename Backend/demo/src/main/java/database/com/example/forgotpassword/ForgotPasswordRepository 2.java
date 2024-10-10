package database.com.example.forgotpassword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    ForgotPassword findByemail(String email);  // Custom method to find by username
}
