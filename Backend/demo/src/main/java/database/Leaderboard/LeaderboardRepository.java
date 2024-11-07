package database.Leaderboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    // Count likes per song using LikedSongs and group by songID
    @Query("SELECT ls.songID AS songID, COUNT(u.userID) AS likeCount " +
            "FROM LikedSongs ls JOIN ls.likedUsers u " +
            "GROUP BY ls.songID " +
            "ORDER BY likeCount DESC")
    List<Object[]> findTopLikedSongs();
}
