package database.com.example.forgotpassword;

import database.User.User;
import database.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing forgot password operations.
 */
@RestController
@Tag(name = "Forgot Password API", description = "Handles password recovery operations.")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    /**
     * Get all forgot password records.
     *
     * @return A list of all `ForgotPassword` records.
     */
    @Operation(summary = "Get all forgot password records", description = "Retrieve all forgot password records in the database.")
    @GetMapping(path = "/forgetPassword")
    public List<ForgotPassword> getAllForgotPassword() {
        return forgotPasswordRepository.findAll();
    }

    /**
     * Get a forgot password record by email.
     *
     * @param email The email of the user.
     * @return The `ForgotPassword` record for the given email, or `null` if not found.
     */
    @Operation(summary = "Get forgot password record by email", description = "Retrieve a specific forgot password record by the user's email.")
    @GetMapping(path = "/forgetPassword/{email}")
    public ForgotPassword getForgetPasswordByID(@PathVariable String email) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findByemail(email);
        return forgotPassword.orElse(null);
    }

    /**
     * Validate security questions and return the user's password.
     *
     * @param forgotPasswordRequest The `ForgotPassword` request containing email and security answers.
     * @return A success message with the user's password or an error message.
     */
    @Operation(summary = "Validate security questions", description = "Validate answers to security questions and retrieve the user's password.")
    @PostMapping(path = "/forgetPassword/validate")
    public String validateSecurityQuestions(@RequestBody ForgotPassword forgotPasswordRequest) {
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByemail(forgotPasswordRequest.getEmail());

        if (!forgotPasswordOptional.isPresent()) {
            return "{\"message\": \"User not found\"}";
        }

        ForgotPassword forgotPassword = forgotPasswordOptional.get();
        if (forgotPassword.getansSecurityQuestion1().equals(forgotPasswordRequest.getansSecurityQuestion1()) &&
                forgotPassword.getansSecurityQuestion2().equals(forgotPasswordRequest.getansSecurityQuestion2())) {

            User user = userRepository.findByemail(forgotPasswordRequest.getEmail());
            if (user != null) {
                return "{\"email\": \"" + forgotPasswordRequest.getEmail() + "\", \"password\": \"" + user.getPassword() + "\"}";
            } else {
                return "{\"message\": \"User not found\"}";
            }
        } else {
            return "{\"message\": \"Security answers do not match\"}";
        }
    }

    /**
     * Create a new forgot password record.
     *
     * @param forgotPassword The new `ForgotPassword` record to create.
     * @return A success or failure message.
     */
    @Operation(summary = "Create forgot password record", description = "Create a new forgot password record in the database.")
    @PostMapping(path = "/forgetPassword")
    public String createForgotPassword(@RequestBody ForgotPassword forgotPassword) {
        if (forgotPassword == null) {
            return failure;
        }
        forgotPasswordRepository.save(forgotPassword);
        return success;
    }

    /**
     * Update an existing forgot password record by email.
     *
     * @param email   The email of the user to update.
     * @param request The updated `ForgotPassword` record.
     * @return The updated `ForgotPassword` record or `null` if not found.
     */
    @Operation(summary = "Update forgot password record", description = "Update an existing forgot password record by the user's email.")
    @PutMapping(path = "/forgetPassword/{email}")
    public ForgotPassword updatePassword(@PathVariable String email, @RequestBody ForgotPassword request) {
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByemail(email);
        if (!forgotPasswordOptional.isPresent()) {
            return null;
        }
        ForgotPassword forgotPassword = forgotPasswordOptional.get();

        forgotPassword.setEmail(request.getEmail());
        forgotPassword.setansSecurityQuestion1(request.getansSecurityQuestion1());
        forgotPassword.setansSecurityQuestion2(request.getansSecurityQuestion2());

        forgotPasswordRepository.save(forgotPassword);
        return forgotPassword;
    }

    /**
     * Delete a forgot password record by email.
     *
     * @param email The email of the user to delete.
     * @return A success or failure message.
     */
    @Operation(summary = "Delete forgot password record", description = "Delete a forgot password record by the user's email.")
    @DeleteMapping(path = "/forgetPassword/{email}")
    public String deleteForgetPasswordByID(@PathVariable String email) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findByemail(email);
        if (!forgotPassword.isPresent()) {
            return failure;
        }
        forgotPasswordRepository.delete(forgotPassword.get());
        return success;
    }
}
