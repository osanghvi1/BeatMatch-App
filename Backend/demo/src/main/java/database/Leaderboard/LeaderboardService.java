package database.Leaderboard;

import database.LikedSongs.LikedSongRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing leaderboard operations.
 * <p>
 * Handles tasks such as resetting and updating the leaderboard based on song likes.
 */
@Service
@Tag(name = "Leaderboard Service", description = "Provides business logic for leaderboard management.")
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private LikedSongRepository likedSongRepository;

    /**
     * Resets and updates the leaderboard.
     * <p>
     * Clears the existing leaderboard and populates it with the top 10 most liked songs
     * from the user-like-song mapping table. Songs are ranked by their like counts.
     */
    public void resetLeaderboard() {
        // Clear the existing leaderboard
        leaderboardRepository.deleteAll();

        // Get top liked songs from the user_like_song_mapping
        List<Object[]> topSongs = likedSongRepository.findTopLikedSongs();

        int rank = 1;
        for (Object[] songData : topSongs) {
            Long songID = (Long) songData[0];
            Long likes = (Long) songData[1];
            if (rank > 10) break;

            // Initialize and set values for leaderboard entry
            Leaderboard leaderboardEntry = new Leaderboard();
            leaderboardEntry.setCategory("General");
            leaderboardEntry.setSongID(songID);
            leaderboardEntry.setRank(rank);
            leaderboardEntry.setLikeCount(likes.intValue());
            leaderboardEntry.setUpdatedTime(LocalDateTime.now());

            leaderboardRepository.save(leaderboardEntry);
            rank++;
        }
    }
}
