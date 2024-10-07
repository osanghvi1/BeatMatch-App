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

    // Get a liked song by songId
    @GetMapping("/{songId}")
    public LikedSongs getLikedSongBySongId(@PathVariable long songId) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongId(songId);
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

    // POST request to like a song (this is the /like endpoint you mentioned)
    @PostMapping("/like")
    public String likeSong(@RequestBody LikedSongs likedSong) {
        // Save the liked song to the repository
        likedSongRepository.save(likedSong);
        return "{\"message\":\"Song liked successfully\"}";
    }

    // Update an existing liked song by songId
    @PutMapping("/{songId}")
    public LikedSongs updateLikedSong(@PathVariable long songId, @RequestBody LikedSongs request) {
        Optional<LikedSongs> likedSongOptional = likedSongRepository.findBySongId(songId);
        if (!likedSongOptional.isPresent()) {
            return null; // If the record with the given songId doesn't exist
        }
        LikedSongs likedSong = likedSongOptional.get();

        // Update fields from the request body
        likedSong.setSongName(request.getSongName());
        likedSong.setSongGenre(request.getSongGenre());
        likedSong.setUserID(request.getUserID());

        // Save the updated record
        likedSongRepository.save(likedSong);
        return likedSong;
    }

    // Delete a liked song by songId
    @DeleteMapping("/{songId}")
    public String deleteLikedSongBySongId(@PathVariable long songId) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongId(songId);
        if (!likedSong.isPresent()) {
            return failure; // If the record with the given ID doesn't exist
        }
        likedSongRepository.deleteBySongId(songId);
        return success;
    }
}
