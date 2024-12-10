package database.Leaderboard;

import database.API.ApiService;
import database.LikedSongs.LikedSongRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Tag(name = "Leaderboard Service", description = "Provides business logic for leaderboard management.")
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private LikedSongRepository likedSongRepository;

    @Autowired
    private ApiService apiService;

    // Temporary in-memory storage
    private final CopyOnWriteArrayList<Leaderboard> top10Leaderboard = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Leaderboard> top100Leaderboard = new CopyOnWriteArrayList<>();

    /**
     * Retrieve the top 10 leaderboard from memory.
     *
     * @return A list of top 10 `Leaderboard` entries.
     */
    public List<Leaderboard> getTop10Leaderboard() {
        return new ArrayList<>(top10Leaderboard);
    }

    /**
     * Retrieve the top 100 leaderboard from memory.
     *
     * @return A list of top 100 `Leaderboard` entries.
     */
    public List<Leaderboard> getTop100Leaderboard() {
        return new ArrayList<>(top100Leaderboard);
    }

    /**
     * Resets and updates both the top 10 and top 100 leaderboards.
     */
    public void resetLeaderboard() {
        // Clear temporary storage
        top10Leaderboard.clear();
        top100Leaderboard.clear();

        // Update top 10 leaderboard
        populateLeaderboard(11, top10Leaderboard);

        // Update top 100 leaderboard
        populateLeaderboard(101, top100Leaderboard);
    }

    /**
     * Populate a leaderboard with a given limit.
     *
     * @param limit     The number of entries to retrieve.
     * @param leaderboard The leaderboard to populate.
     */
    private void populateLeaderboard(int limit, List<Leaderboard> leaderboard) {
        List<Object[]> topSongs = likedSongRepository.findTopLikedSongs(limit);

        int rank = 1;
        for (Object[] songData : topSongs) {
            Long songID = (Long) songData[0];
            Long likes = (Long) songData[1];

            try {
                // Fetch song details from Deezer API
                JSONObject trackDetails = apiService.getTrackDetailsById(songID);

                Leaderboard entry = new Leaderboard();
                entry.setCategory("General");
                entry.setSongID(songID);
                entry.setRank(rank);
                entry.setLikeCount(likes.intValue());
                entry.setUpdatedTime(LocalDateTime.now());
                entry.setSongName(trackDetails.getString("title"));
                entry.setArtist(trackDetails.getJSONObject("artist").getString("name"));
                entry.setAlbumCover(trackDetails.getJSONObject("album").getString("cover"));

                leaderboard.add(entry);
                rank++;
            } catch (Exception e) {
                System.err.println("Error processing songID: " + songID);
                e.printStackTrace();
            }
        }
    }


    @Scheduled(cron = "0 0 0 * * MON") // Every Monday at 12:00 AM
    public void scheduledResetLeaderboard() {
        resetLeaderboard();
    }
}