package database.User;

import database.Notifications.NotificationService;
import database.DislikedSongs.DislikedSongs;
import database.DislikedSongs.DislikedSongsRepository;
import database.LikedSongs.LikedSongRepository;
import database.LikedSongs.LikedSongs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
    public class UserController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DislikedSongsRepository dislikedSongsRepository;

    @Autowired
    private LikedSongRepository likedSongRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();}

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){return userRepository.findById(id);}

    @GetMapping(path = "/users/{email}/{password}")
    public int getUserByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        if (userRepository.findByEmailAndPassword(email, password) != null) {
            return userRepository.findByEmailAndPassword(email, password).getUserID();
        }
        else{
            return -1;
        }
    }

    //get disliked songs based on user
    @GetMapping(path = "users/dislikedSongs/{id}")
    public List<DislikedSongs> getDislikedSongsByUser(@PathVariable int id){
        Set<DislikedSongs> dislikedSongs = userRepository.findById(id).getDislikedSongs();
        notificationService.sendNotification("Getting Disliked Songs");
        return new ArrayList<>(dislikedSongs);
    }

    //get disliked songs based on user
    @GetMapping(path = "users/likedSongs/{id}")
    public List<LikedSongs> getLikedSongsByUser(@PathVariable int id){
        Set<LikedSongs> likedSongs = userRepository.findById(id).getLikedSongs();
        return new ArrayList<>(likedSongs);
    }

    @Transactional
    @PostMapping(path = "/users/dislikeSong/{userId}")
    public String dislikeSong(@PathVariable int userId, @RequestBody DislikedSongs dislikedSong) {
        //create stuff for song to add
        if(dislikedSong == null){
            return "Invalid Song Format";
        }
        //get the user that disliked the song
        User user = userRepository.findById(userId);
        if (user == null) {
            return "User not found";
        }

        int songID = dislikedSong.getSongID();

        //if the song doesn't already exist in the database as a disliked song
        if (dislikedSongsRepository.findById(songID) == null) {
            dislikedSongsRepository.save(dislikedSong);
        }

        //add user to disliked users
        dislikedSong.getDislikedUsers().add(user);

        //add song to user's disliked songs
        user.getDislikedSongs().add(dislikedSong);

        dislikedSongsRepository.save(dislikedSong);
        userRepository.save(user);

        return "Disliked Song Added";
    }

    @Transactional
    @PostMapping(path = "/users/likeSong/{userId}")
    public String likeSong(@PathVariable int userId, @RequestBody LikedSongs likedSong) {
        if(likedSong == null){
            return "Invalid Song Format";
        }
        //get the user that disliked the song
        User user = userRepository.findById(userId);
        if (user == null) {
            return "User not found";
        }

        //create stuff for song to add
        long songID = likedSong.getSongID();

        //if the song doesn't already exist in the database as a disliked song
        if (likedSongRepository.findById(songID) == null) {
                likedSongRepository.save(likedSong);
        }

        //add user to disliked users
        likedSong.getLikedUsers().add(user);

        //add song to user's disliked songs
        user.getLikedSongs().add(likedSong);

        likedSongRepository.save(likedSong);
        userRepository.save(user);

        return "Liked Song Added";
    }

    @PostMapping(path = "/createUser")
    public int createUser(@RequestBody User user){
        if (user == null){
            return -1;
        }
        userRepository.save(user);
        return user.getUserID();
    }

    @DeleteMapping(path = "/users/delete/{id}")
    public User deleteGenres(@PathVariable int id) {
        User deletedUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return deletedUser;
    }
}
