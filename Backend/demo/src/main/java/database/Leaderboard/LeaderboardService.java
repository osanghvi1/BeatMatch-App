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

        // Fetch all liked songs and count likes per song
        List<LikedSongs> likedSongs = likedSongRepository.findAll();

        // Group and sort by the number of likes in descending order
        likedSongs.sort((s1, s2) -> Long.compare(s2.getSongID(), s1.getSongID())); // You may need to customize sorting

        // Create new leaderboard entries based on sorted songs
        int rank = 1;
        for (LikedSongs likedSong : likedSongs) {
            if (rank > 10) break; // Limit to top 10 songs, for example
            Leaderboard leaderboardEntry = new Leaderboard(
                    "General", // Category can be adjusted
                    likedSong.getUserID(),
                    likedSong.getSongID(),
                    rank,
                    LocalDateTime.now()
            );
            leaderboardRepository.save(leaderboardEntry);
            rank++;
        }
    }
}
