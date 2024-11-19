package database.com.example.forgotpassword;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing `ForgotPassword` entities.
 * Provides methods for database operations like retrieving and managing forgot password records.
 */
@Tag(name = "Forgot Password Repository", description = "Repository for forgot password database operations.")
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, String> {

    /**
     * Find a forgot password record by email.
     *
     * @param email The email to search for.
     * @return An `Optional<ForgotPassword>` containing the record if found, or empty otherwise.
     */
    @Operation(summary = "Find by email", description = "Retrieve a forgot password record by the user's email.")
    Optional<ForgotPassword> findByemail(String email);
}
