package database.LikedSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Long> {

    // Find a liked song by songID
    Optional<LikedSongs> findBySongID(Long songID);

    // Find all songs liked by a specific user
    List<LikedSongs> findAllByUserID(Long userID);

    // Delete a liked song by songID
    void deleteBySongID(Long songID);
}
