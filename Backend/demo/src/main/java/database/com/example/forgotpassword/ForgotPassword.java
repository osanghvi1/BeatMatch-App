package database.com.example.forgotpassword;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * Represents a user's forgot password record.
 * <p>
 * This entity stores email and answers to security questions
 * used for password recovery.
 */
@Entity
@Schema(description = "Represents a forgot password record with email and security question answers.")
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the forgot password record.", example = "1")
    private int id;

    @Column(unique = true)
    @Schema(description = "User's email address, must be unique.", example = "user@example.com")
    private String email;

    @Schema(description = "Answer to the first security question.", example = "My first pet's name")
    private String ansSecurityQuestion1;

    @Schema(description = "Answer to the second security question.", example = "My favorite teacher")
    private String ansSecurityQuestion2;

    // Constructors
    public ForgotPassword(String email, String ansSecurityQuestion1, String ansSecurityQuestion2) {
        this.email = email;
        this.ansSecurityQuestion1 = ansSecurityQuestion1;
        this.ansSecurityQuestion2 = ansSecurityQuestion2;
    }

    public ForgotPassword() {}

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getansSecurityQuestion1() {
        return ansSecurityQuestion1;
    }

    public void setansSecurityQuestion1(String ansSecurityQuestion1) {
        this.ansSecurityQuestion1 = ansSecurityQuestion1;
    }

    public String getansSecurityQuestion2() {
        return ansSecurityQuestion2;
    }

    public void setansSecurityQuestion2(String ansSecurityQuestion2) {
        this.ansSecurityQuestion2 = ansSecurityQuestion2;
    }
}
