package com.example.song_request2.Liked_Song;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Integer> {

    // Updated method to use long type for songId
    Optional<LikedSongs> findBySongId(long songId);

    @Transactional
    @Modifying
    @Query("DELETE FROM LikedSongs ls WHERE ls.songId = :songId")
    void deleteBySongId(long songId);
}
