package database.User;

import database.GenrePreferences.GenrePreferences;
import database.Notifications.NotificationService;
import database.DislikedSongs.DislikedSongs;
import database.DislikedSongs.DislikedSongsRepository;
import database.LikedSongs.LikedSongRepository;
import database.LikedSongs.LikedSongs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)})
    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();}

    @Operation(summary = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable int id){return userRepository.findById(id);}

    @Operation(summary = "Authenticate a user by email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid email or password ",
                    content = @Content)})
    @GetMapping(path = "/users/{email}/{password}")
    public int getUserByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        if (userRepository.findByEmailAndPassword(email, password) != null) {
            notificationService.sendNotification("Getting Specific User");
            if(userRepository.findByEmailAndPassword(email, password).getAccountStatus() == -1) {
                return -2;
            }

            return userRepository.findByEmailAndPassword(email, password).getUserID();
        }
        else{
            return 404;
        }
    }

    @Operation(summary = "Get disliked songs by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user's songs",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @GetMapping(path = "users/dislikedSongs/{id}")
    public Set<DislikedSongs> getDislikedSongsByUser(@PathVariable int id){
        return userRepository.findById(id).getDislikedSongs();
    }

    @Operation(summary = "Get liked songs by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user's songs",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @GetMapping(path = "users/likedSongs/{id}")
    public Set<LikedSongs> getLikedSongsByUser(@PathVariable int id){
        return userRepository.findById(id).getLikedSongs();
    }


    @Operation(summary = "Dislike a song by userID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "disliked the song",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid user id or song id supplied",
                    content = @Content)})
    @Transactional
    @PostMapping(path = "/users/dislikeSong/{userId}")
    public String dislikeSong(@PathVariable int userId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Song to Dislike", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class),
                    examples = @ExampleObject(value = "{ \"songID\": \"123456\", \"songTitle\": \"ABC's\", \"genre\": \"Kids\" }")))@RequestBody DislikedSongs dislikedSong) {
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


    @Operation(summary = "Like a song by userID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "liked the song",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid user id or song id supplied",
                    content = @Content)})
    @Transactional
    @PostMapping(path = "/users/likeSong/{userId}")
    public String likeSong(@PathVariable int userId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Song to Like", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class),
                    examples = @ExampleObject(value = "{ \"songID\": \"123456\", \"songTitle\": \"ABC's\", \"genre\": \"Kids\" }")))@RequestBody LikedSongs likedSong) {
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

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created a user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "unable to create user",
                    content = @Content)})
    @PostMapping(path = "/createUser")
    public int createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class),
                    examples = @ExampleObject(value = "{ \"firstName\": \"Lawson\", \"lastName\": \"Port\", \"email\": \"lport@iastate.edu\", \"userName\": \"lport\", \"password\": \"password\", \"accountVisibility\": \"1\", \"accountStatus\": \"1\" }")))@RequestBody User user){
        if (user == null){
            return -1;
        }
        userRepository.save(user);
        return user.getUserID();
    }

    @PutMapping(path = "/user/edit/{uid}")
    public String editUser(@PathVariable int uid, @RequestBody User userToModify){
        User user = userRepository.findById(uid);
        String newFirstname = userToModify.getFirstName();
        String newLastname = userToModify.getLastName();
        String newUsername = userToModify.getUserName();
        String newPassword = userToModify.getPassword();
        int newAccountVisibility = userToModify.getAccountVisibility();
        int newAccountStatus = userToModify.getAccountStatus();


        if (user == null){
            return "Edit : Failure";
        }
        else{
            if (newFirstname != null){
                user.setFirstName(newFirstname);
            }
            if (newLastname != null){
                user.setLastName(newLastname);
            }
            if (newUsername != null){
                user.setUserName(newUsername);
            }
            if (newPassword != null){
                user.setPassword(newPassword);
            }
            if(newAccountVisibility != user.getAccountVisibility()){
                user.setAccountVisibility(newAccountVisibility);
            }
            if(newAccountStatus != user.getAccountStatus()){
                user.setAccountStatus(newAccountStatus);
            }
            userRepository.save(user);
            return "Edit : Success";
        }
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid user id",
                    content = @Content)})
    @DeleteMapping(path = "/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        User deletedUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return "User deleted Successfully";
    }
}
