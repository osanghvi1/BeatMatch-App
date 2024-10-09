package database.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    public class UserController {

    @Autowired
        private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    public List<User> getAllUsers() {return userRepository.findAll();}

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    @GetMapping(path = "/users/{email}/{password}")
    String getUserByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        if (userRepository.findByEmailAndPassword(email, password) != null) {
            return "Success! UserID for specified email: " + userRepository.findByEmailAndPassword(email, password).getUserID();
        }
        else{
            return "Failed to Authenticate User";
        }
    }

    @PostMapping(path = "/createUser")
    String createUser(@RequestBody User user){
        if (user == null){
            return failure;
        }
        userRepository.save(user);
        return success;
    }

        @GetMapping(path = "/users/email/{email}")
        public String getUserPasswordByUsername(@PathVariable String username) {
            User user = userRepository.findByemail(username);  // Use findByUserName here
            if (user != null) {
                return "Password for user " + username + ": " + user.getPassword();
            } else {
                return "User not found";
            }
        }


}
