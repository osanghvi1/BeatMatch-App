package database.Leaderboard;

import database.API.ApiService;
import database.LikedSongs.LikedSongRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Tag(name = "Leaderboard Service", description = "Provides business logic for leaderboard management.")
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private LikedSongRepository likedSongRepository;

    @Autowired
    private ApiService apiService;

    public List<Leaderboard> getLeaderboard(int limit) {
        List<Object[]> topSongs = likedSongRepository.findTopLikedSongs(limit);
        List<Leaderboard> leaderboard = new ArrayList<>();

        int rank = 1;
        for (Object[] songData : topSongs) {
            Long songID = (Long) songData[0];
            Long likes = (Long) songData[1];

            try {
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
                System.err.println("Error fetching details for songID: " + songID);
                e.printStackTrace();
            }
        }
        return leaderboard;
    }

    public void resetLeaderboard() {
        leaderboardRepository.deleteAll();

        List<Object[]> topSongs = likedSongRepository.findTopLikedSongs(100);
        int rank = 1;

        for (Object[] songData : topSongs) {
            Long songID = (Long) songData[0];
            Long likes = (Long) songData[1];

            try {
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

                leaderboardRepository.save(entry);
                rank++;
            } catch (Exception e) {
                System.err.println("Error processing songID: " + songID);
                e.printStackTrace();
            }
        }
    }
}
