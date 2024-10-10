package database.com.example.forgotpassword;

import database.User.User;
import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Get all ForgotPassword records
    @GetMapping(path = "/forgetPassword")
    public List<ForgotPassword> getAllForgotPassword() {
        return forgotPasswordRepository.findAll();
    }

    // Get a single ForgotPassword record by email
    @GetMapping(path = "/forgetPassword/{email}")
    public ForgotPassword getForgetPasswordByID(@PathVariable String email) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findByemail(email);
        return forgotPassword.orElse(null); // Return the object or null if not present
    }

    // Validate security questions and return the password
    @PostMapping(path = "/forgetPassword/validate")
    public String validateSecurityQuestions(@RequestBody ForgotPassword forgotPasswordRequest) {
        // Fetch the ForgotPassword record by email
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByemail(forgotPasswordRequest.getEmail());

        if (!forgotPasswordOptional.isPresent()) {
            return "{\"message\": \"User not found\"}";
        }

        ForgotPassword forgotPassword = forgotPasswordOptional.get();

        // Validate the answers to the security questions
        if (forgotPassword.getansSecurityQuestion1().equals(forgotPasswordRequest.getansSecurityQuestion1()) &&
                forgotPassword.getansSecurityQuestion2().equals(forgotPasswordRequest.getansSecurityQuestion2())) {

            // Fetch the password from the User entity
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

    // Create a new ForgotPassword record
    @PostMapping(path = "/forgetPassword")
    public String createForgotPassword(@RequestBody ForgotPassword forgotPassword) {
        if (forgotPassword == null) {
            return failure;
        }
        forgotPasswordRepository.save(forgotPassword);
        return success;
    }

    // Update an existing ForgotPassword record by email
    @PutMapping(path = "/forgetPassword/{email}")
    public ForgotPassword updatePassword(@PathVariable String email, @RequestBody ForgotPassword request) {
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByemail(email);
        if (!forgotPasswordOptional.isPresent()) {
            return null; // If the record with the given email doesn't exist
        }
        ForgotPassword forgotPassword = forgotPasswordOptional.get();

        // Update fields from the request body
        forgotPassword.setEmail(request.getEmail());
        forgotPassword.setansSecurityQuestion1(request.getansSecurityQuestion1());
        forgotPassword.setansSecurityQuestion2(request.getansSecurityQuestion2());

        // Save the updated record
        forgotPasswordRepository.save(forgotPassword);
        return forgotPassword;
    }

    // Delete a ForgotPassword record by email
    @DeleteMapping(path = "/forgetPassword/{email}")
    public String deleteForgetPasswordByID(@PathVariable String email) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findByemail(email);
        if (!forgotPassword.isPresent()) {
            return failure; // If the record with the given email doesn't exist
        }
        forgotPasswordRepository.delete(forgotPassword.get()); // Use the found record and delete it
        return success;
    }

}
