package database.Leaderboard;

import database.LikedSongs.LikedSongs;
import database.LikedSongs.LikedSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private LikedSongRepository likedSongRepository;

    // Scheduled to run every week (e.g., Sunday at midnight)
    @Scheduled(cron = "0 0 0 * * SUN")
    public void resetLeaderboardWeekly() {
        // Clear the existing leaderboard
        leaderboardRepository.deleteAll();

        // Fetch top liked songs
        List<Object[]> topLikedSongs = likedSongRepository.findTopLikedSongs();

        // Iterate over the results and populate the leaderboard with the top 10
        int rank = 1;
        for (Object[] result : topLikedSongs) {
            if (rank > 10) break; // Limit to top 10 songs

            Long songID = (Long) result[0];
            Long likes = (Long) result[1];

            // Create a new leaderboard entry
            Leaderboard leaderboardEntry = new Leaderboard(
                    "Top Liked Songs", // Category
                    null, // userID can be null, as leaderboard is based on total likes, not individual users
                    songID,
                    rank,
                    LocalDateTime.now()
            );
            leaderboardRepository.save(leaderboardEntry);
            rank++;
        }
    }
}
