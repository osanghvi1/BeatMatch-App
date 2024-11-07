package database.LikedSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Long> {

    // Find a liked song by songID
    Optional<LikedSongs> findBySongID(Long songID);

    // Find all songs liked by a specific user
    List<LikedSongs> findAllByUserID(Long userID);

    // Delete a liked song by songID
    void deleteBySongID(Long songID);

    // Custom query to count likes per song, sorted by likes in descending order
    @Query("SELECT ls.songID AS songID, COUNT(ls.songID) AS likes FROM LikedSongs ls GROUP BY ls.songID ORDER BY likes DESC")
    List<Object[]> findTopLikedSongs();
}
