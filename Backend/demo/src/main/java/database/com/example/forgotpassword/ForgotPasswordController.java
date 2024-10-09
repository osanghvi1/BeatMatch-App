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
    private UserRepository userRepository; // Add UserRepository to fetch the password

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Get all ForgotPassword records
    @GetMapping(path = "/forgetPassword")
    public List<ForgotPassword> getAllForgotPassword() {
        return forgotPasswordRepository.findAll();
    }

    // Get a single ForgotPassword record by ID
    @GetMapping(path = "/forgetPassword/{email}")
    public ForgotPassword getForgetPasswordByID(@PathVariable int email) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findById(email);
        return forgotPassword.orElse(null); // Returns null if not found
    }

    // Get a ForgotPassword record by email and retrieve the user's password from User table
    @GetMapping(path = "/forgetPassword/email/{email}")
    public String getForgetPasswordByemail(@PathVariable String email) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByemail(email);

        // Fetch the password from the User table
        User user = userRepository.findByemail(email);
        if (user != null) {
            String password = user.getPassword(); // Fetch the password from the User entity
            return "{\"forgotPassword\": " + forgotPassword.toString() + ", \"password\": \"" + password + "\"}";
        } else {
            return "{\"message\": \"User not found\"}";
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

    // Update an existing ForgotPassword record by ID
    @PutMapping(path = "/forgetPassword/{email}")
    public ForgotPassword updatePassword(@PathVariable int email, @RequestBody ForgotPassword request) {
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findById(email);
        if (!forgotPasswordOptional.isPresent()) {
            return null; // If the record with the given ID doesn't exist
        }
        ForgotPassword forgotPassword = forgotPasswordOptional.get();

        // Update fields from the request body
        forgotPassword.setEmail(request.getEmail());
        forgotPassword.setansSecurityQuestion1(request.getansSecurityQuestion1()); // Fixed method name for answer
        forgotPassword.setansSecurityQuestion2(request.getansSecurityQuestion2()); // Fixed method name for answer

        // Save the updated record
        forgotPasswordRepository.save(forgotPassword);
        return forgotPassword;
    }

    // Delete a ForgotPassword record by ID
    @DeleteMapping(path = "/forgetPassword/{email}")
    public String deleteForgetPasswordByID(@PathVariable int email) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findById(email);
        if (!forgotPassword.isPresent()) {
            return failure; // If the record with the given ID doesn't exist
        }
        forgotPasswordRepository.deleteById(email);
        return success;
    }
}
