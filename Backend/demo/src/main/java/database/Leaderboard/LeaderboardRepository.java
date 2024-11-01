package database.Leaderboard;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    // Find all leaderboard entries by category, sorted by rank
    List<Leaderboard> findByCategoryOrderByRank(String category);

    // Custom method to find a specific leaderboard entry by song ID and category
    Leaderboard findBySongIDAndCategory(Long songID, String category);
}
