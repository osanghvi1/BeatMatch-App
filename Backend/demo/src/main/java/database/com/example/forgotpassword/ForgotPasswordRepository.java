package database.com.example.forgotpassword;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, String> {
    Optional<ForgotPassword> findByemail(String email);  // Change return type to Optional<ForgotPassword>
}
