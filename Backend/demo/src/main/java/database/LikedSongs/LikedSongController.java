package database.LikedSongs;

import database.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing liked songs.
 */
@RestController
@RequestMapping("/likedSongs")
@Tag(name = "Liked Songs API", description = "Handles operations related to liked songs.")
public class LikedSongController {

    @Autowired
    private LikedSongRepository likedSongRepository;

    @Autowired
    private UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    /**
     * Retrieve all liked songs.
     *
     * @return A list of all `LikedSongs` entities.
     */
    @Operation(summary = "Get all liked songs", description = "Retrieve a list of all liked songs.")
    @GetMapping
    public List<LikedSongs> getAllLikedSongs() {
        return likedSongRepository.findAll();
    }

    /**
     * Retrieve a liked song by song ID.
     *
     * @param songID The ID of the song to retrieve.
     * @return The `LikedSongs` entity for the given song ID, or `null` if not found.
     */
    @Operation(summary = "Get a liked song by ID", description = "Retrieve a specific liked song by its song ID.")
    @GetMapping("/{songID}")
    public LikedSongs getLikedSongBySongId(@PathVariable long songID) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongID(songID);
        return likedSong.orElse(null);
    }

    /**
     * Delete a liked song by song ID.
     *
     * @param songID The ID of the song to delete.
     * @return A success or failure message.
     */
    @Operation(summary = "Delete a liked song by ID", description = "Delete a specific liked song by its song ID.")
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
