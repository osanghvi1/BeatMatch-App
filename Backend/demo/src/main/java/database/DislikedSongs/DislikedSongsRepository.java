package database.DislikedSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DislikedSongsRepository extends JpaRepository<DislikedSongs, Integer> {
    DislikedSongs findById(int id);

    @Transactional
    void deleteById(int id);

}
