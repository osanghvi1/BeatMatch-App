package database.SimilarGenres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SimilarGenresRepository extends JpaRepository<SimilarGenres, Integer> {

    SimilarGenres findById(int id);

    SimilarGenres findByGenreName(String genreName);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteByGenreName(String genreName);


}
