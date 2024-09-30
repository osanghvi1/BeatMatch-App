package com.example.forgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Get all ForgotPassword records
    @GetMapping(path = "/forgetPassword")
    public List<ForgotPassword> getAllForgotPassword() {
        return forgotPasswordRepository.findAll();
    }

    // Get a single ForgotPassword record by ID
    @GetMapping(path = "/forgetPassword/{id}")
    public ForgotPassword getForgetPasswordByID(@PathVariable int id) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findById(id);
        return forgotPassword.orElse(null); // Returns null if not found
    }

    // Get a single ForgotPassword record by username
    @GetMapping(path = "/forgetPassword/username/{username}")
    public ForgotPassword getForgetPasswordByUsername(@PathVariable String username) {
        return forgotPasswordRepository.findByUsername(username); // Return the record by username
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
    @PutMapping(path = "/forgetPassword/{id}")
    public ForgotPassword updatePassword(@PathVariable int id, @RequestBody ForgotPassword request) {
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findById(id);
        if (!forgotPasswordOptional.isPresent()) {
            return null; // If the record with the given ID doesn't exist
        }
        ForgotPassword forgotPassword = forgotPasswordOptional.get();

        // Update fields from the request body
        forgotPassword.setUsername(request.getUsername());
        forgotPassword.setSecurityQuestion1(request.getSecurityQuestion1());
        forgotPassword.setansSecurityQuestion1(request.getansSecurityQuestion1()); // Fixed method name for answer
        forgotPassword.setSecurityQuestion2(request.getSecurityQuestion2());
        forgotPassword.setansSecurityQuestion2(request.getansSecurityQuestion2()); // Fixed method name for answer

        // Save the updated record
        forgotPasswordRepository.save(forgotPassword);
        return forgotPassword;
    }

    // Delete a ForgotPassword record by ID
    @DeleteMapping(path = "/forgetPassword/{id}")
    public String deleteForgetPasswordByID(@PathVariable int id) {
        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findById(id);
        if (!forgotPassword.isPresent()) {
            return failure; // If the record with the given ID doesn't exist
        }
        forgotPasswordRepository.deleteById(id);
        return success;
    }
}
