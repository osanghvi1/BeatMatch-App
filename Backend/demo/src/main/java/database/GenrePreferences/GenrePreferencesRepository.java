package database.GenrePreferences;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GenrePreferencesRepository extends JpaRepository<GenrePreferences, Integer> {


    //finds user's genre prefrences based on id
    GenrePreferences findById(int id);

    //deletes users genere preferences by id
    @Transactional
    void deleteById(int id);



}
