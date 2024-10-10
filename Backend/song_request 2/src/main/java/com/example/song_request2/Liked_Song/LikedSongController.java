package com.example.song_request2.Liked_Song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likedSongs")
public class LikedSongController {

    @Autowired
    private LikedSongRepository likedSongRepository;

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

    // Create a new liked song record
    @PostMapping
    public String createLikedSong(@RequestBody LikedSongs likedSong) {
        if (likedSong == null) {
            return failure;
        }
        likedSongRepository.save(likedSong);
        return success;
    }

    // POST request to like a song
    @PostMapping("/like")
    public String likeSong(@RequestBody LikedSongs likedSong) {
        likedSongRepository.save(likedSong);
        return "{\"message\":\"Song liked successfully\"}";
    }

    // Update an existing liked song by songID
    @PutMapping("/{songID}")
    public LikedSongs updateLikedSong(@PathVariable long songID, @RequestBody LikedSongs request) {
        Optional<LikedSongs> likedSongOptional = likedSongRepository.findBySongID(songID);
        if (!likedSongOptional.isPresent()) {
            return null; // If the record with the given songID doesn't exist
        }
        LikedSongs likedSong = likedSongOptional.get();
        likedSong.setUserID(request.getUserID());

        likedSongRepository.save(likedSong);
        return likedSong;
    }

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

    // Get all songs liked by a specific user
    @GetMapping("/user/{userID}")
    public List<LikedSongs> getLikedSongsByUserId(@PathVariable long userID) {
        return likedSongRepository.findAllByUserID(userID);
    }
}
