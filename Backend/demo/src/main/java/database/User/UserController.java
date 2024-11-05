package database.User;

import database.Notifications.NotificationService;
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

    @Autowired
    private NotificationService notificationService;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
       // notificationService.sendNotification("BallsLMAOOOOO");
        return userRepository.findAll();}

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){return userRepository.findById(id);}

    @GetMapping(path = "/users/{email}/{password}")
    int getUserByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        if (userRepository.findByEmailAndPassword(email, password) != null) {
            return userRepository.findByEmailAndPassword(email, password).getUserID();
        }
        else{
            return -1;
        }
    }

    @PostMapping(path = "/createUser")
    int createUser(@RequestBody User user){
        if (user == null){
            return -1;
        }
        userRepository.save(user);
        return user.getUserID();
    }

    @DeleteMapping(path = "/users/delete/{id}")
    User deleteGenres(@PathVariable int id) {
        User deletedUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return deletedUser;
    }
}
