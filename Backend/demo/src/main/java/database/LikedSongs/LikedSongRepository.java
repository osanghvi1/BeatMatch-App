package database.LikedSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Long> {

    // Corrected method name to match the field in the entity
    Optional<LikedSongs> findBySongID(Long songID);

    // Delete by songID
    @Transactional
    void deleteBySongID(Long songID);
}
