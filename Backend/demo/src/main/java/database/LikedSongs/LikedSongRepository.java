package database.LikedSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Long> {

    // Corrected method name to match the field in the entity
    Optional<LikedSongs> findBySongID(Long songID);

    // Delete by songID
    @Transactional
    void deleteBySongID(Long songID);
    // Query to count likes per song from the user_like_song_mapping table, ordered by like count descending
    @Query(value = "SELECT song_id, COUNT(user_id) AS likes FROM user_like_song_mapping GROUP BY song_id ORDER BY likes DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTopLikedSongs();
}
