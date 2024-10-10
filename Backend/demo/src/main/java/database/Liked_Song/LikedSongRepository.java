package database.Liked_Song;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Long> {

    // Corrected method name to match the field in the entity
    Optional<LikedSongs> findBySongID(Long songID);

    // Find all songs liked by a specific user
    List<LikedSongs> findAllByUserID(Long userID);

    // Delete by songID
    void deleteBySongID(Long songID);
}
