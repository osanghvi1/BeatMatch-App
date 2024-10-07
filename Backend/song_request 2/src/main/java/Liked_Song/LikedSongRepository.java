package Liked_Song;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSongs, Integer> {
    Optional<LikedSongs> findBySongid(int Songid);

    @Transactional
    @Modifying
    @Query("DELETE FROM LikedSongs ls WHERE ls.Songid = :Songid")
    void deleteBySongid(int Songid);
}
