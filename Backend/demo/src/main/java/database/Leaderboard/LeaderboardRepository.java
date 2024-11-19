package database.Leaderboard;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing leaderboard entries.
 * <p>
 * Provides methods to retrieve and manage leaderboard data, including top-liked songs.
 */
@Tag(name = "Leaderboard Repository", description = "Handles database operations for leaderboard entries and top-liked songs.")
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    /**
     * Retrieves the top liked songs and their like counts.
     * <p>
     * Groups songs by their IDs and counts likes for each song. Results are ordered by like count in descending order.
     *
     * @return A list of objects, where each object is an array containing:
     *         - `songID` (Long): The ID of the song.
     *         - `likeCount` (Long): The total number of likes the song has received.
     */
    @Query("SELECT ls.songID AS songID, COUNT(u.userID) AS likeCount " +
            "FROM LikedSongs ls JOIN ls.likedUsers u " +
            "GROUP BY ls.songID " +
            "ORDER BY likeCount DESC")
    List<Object[]> findTopLikedSongs();
}
