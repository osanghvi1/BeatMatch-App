package database.LikedSongs;

import database.DislikedSongs.DislikedSongs;
import database.User.User;
import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/likedSongs")
public class LikedSongController {

    @Autowired
    private LikedSongRepository likedSongRepository;

    @Autowired
    private UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Get all liked songs
    @GetMapping
    public List<LikedSongs> getAllLikedSongs() {
        return likedSongRepository.findAll();
    }

    // Get a liked song by songID
    @GetMapping("/{songID}")
    public LikedSongs getLikedSongBySongId(@PathVariable long songID) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongID(songID);
        return likedSong.orElse(null); // Returns null if not found
    }


    /*
    @PostMapping(path = "/likeSong/{userId}")
    public String addSong(@PathVariable int userId, @RequestBody LikedSongs likedSong) {
        //create stuff for song to add
        long songID = likedSong.getSongID();

        //if the song doesn't already exist in the database as a disliked song
        if (likedSongRepository.findById(songID) == null) {
            likedSongRepository.save(likedSong);
        }
        if(likedSongRepository.find)

        //get the user that disliked the song
        User user = userRepository.findById(userId);

        //create user set
        Set<User> userSet = new HashSet<>();
        userSet.add(user);


        dislikedSong.setDislikedUsers(userSet);

        dislikedSongsRepository.save(dislikedSong);

        return "Disliked Song Added";
    }
     */

    // Delete a liked song by songID
    @DeleteMapping("/{songID}")
    public String deleteLikedSongBySongID(@PathVariable long songID) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongID(songID);
        if (!likedSong.isPresent()) {
            return failure; // If the record with the given ID doesn't exist
        }
        likedSongRepository.deleteBySongID(songID);
        return success;
    }

}
