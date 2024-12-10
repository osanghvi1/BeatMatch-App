package database.LikedSongs;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing liked songs.
 * <p>
 * Provides methods for CRUD operations and custom queries on liked songs.
 */
@Tag(name = "Liked Songs Repository", description = "Handles database operations for liked songs.")
public interface LikedSongRepository extends JpaRepository<LikedSongs, Long> {

    /**
     * Find a liked song by its song ID.
     *
     * @param songID The ID of the song to retrieve.
     * @return An `Optional<LikedSongs>` containing the liked song if found, or empty otherwise.
     */
    Optional<LikedSongs> findBySongID(Long songID);

    /**
     * Delete a liked song by its song ID.
     *
     * @param songID The ID of the song to delete.
     */
    @Transactional
    void deleteBySongID(Long songID);

    /**
     * Retrieve the top liked songs with their like counts.
     * <p>
     * Groups songs by their IDs and counts likes for each song. Results are ordered by like count in descending order, limited to the top 10.
     *
     * @return A list of objects, where each object is an array containing:
     *         - `song_id` (Long): The ID of the song.
     *         - `likes` (Long): The total number of likes the song has received.
     */
    @Query(value = "SELECT song_id, COUNT(user_id) AS likes " +
            "FROM user_like_song_mapping " +
            "GROUP BY song_id " +
            "ORDER BY likes DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopLikedSongs(int limit);
}
