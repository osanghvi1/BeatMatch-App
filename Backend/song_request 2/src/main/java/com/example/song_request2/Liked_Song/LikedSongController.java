package com.example.song_request2.Liked_Song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class LikedSongController {

    @Autowired
    private LikedSongRepository likedSongRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Get all liked songs
    @GetMapping(path = "/likedSongs")
    public List<LikedSongs> getAllLikedSongs() {
        return likedSongRepository.findAll();
    }

    // Get a liked song by songId
    @GetMapping(path = "/likedSongs/{songId}")
    public LikedSongs getLikedSongBySongId(@PathVariable int songId) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongId(songId);
        return likedSong.orElse(null); // Returns null if not found
    }

    // Create a new liked song record
    @PostMapping(path = "/likedSongs")
    public String createLikedSong(@RequestBody LikedSongs likedSong) {
        if (likedSong == null) {
            return failure;
        }
        likedSongRepository.save(likedSong);
        return success;
    }

    // Update an existing liked song by songId
    @PutMapping(path = "/likedSongs/{songId}")
    public LikedSongs updateLikedSong(@PathVariable int songId, @RequestBody LikedSongs request) {
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
    @DeleteMapping(path = "/likedSongs/{songId}")
    public String deleteLikedSongBySongId(@PathVariable int songId) {
        Optional<LikedSongs> likedSong = likedSongRepository.findBySongId(songId);
        if (!likedSong.isPresent()) {
            return failure; // If the record with the given ID doesn't exist
        }
        likedSongRepository.deleteBySongId(songId);
        return success;
    }
}
