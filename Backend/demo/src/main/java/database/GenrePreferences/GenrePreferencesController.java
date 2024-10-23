package database.GenrePreferences;

import database.User.User;
import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenrePreferencesController {

    @Autowired
    private GenrePreferencesRepository genrePreferencesRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/userGenres")
    List<GenrePreferences> getAllGenrePreferences(){
       return genrePreferencesRepository.findAll();
    }

    @GetMapping(path = "/userGenres/{id}")
    GenrePreferences getGenrePreferencesById(@PathVariable int id){
        return genrePreferencesRepository.findById(id);
    }

    @PostMapping(path = "/userGenres/create")
    String createGenrePreferences(@RequestBody GenrePreferences genrePreferences){
        if (genrePreferences == null){
            return "Creation : Failure";
        }
        else{
            int id = genrePreferences.getUserID();
            User currentUser = userRepository.findById(id);
            genrePreferencesRepository.save(genrePreferences);
            currentUser.setGenrePreferences(genrePreferences);
            userRepository.save(currentUser);
            return "Creation : Success";
        }
    }

    @PutMapping(path = "/userGenres/edit/{id}")
    String editGenrePreferences(@PathVariable int id, @RequestBody GenrePreferences genrePreferences){
        GenrePreferences genres = genrePreferencesRepository.findById(id);
        String newGenre1 = genrePreferences.getGenre1();
        String newGenre2 = genrePreferences.getGenre2();
        String newGenre3 = genrePreferences.getGenre3();


        if (genres == null){
            return "Edit : Failure";
        }
        else{
            if (newGenre1 != null){
                genres.setGenre1(newGenre1);
            }
            if (newGenre2 != null){
                genres.setGenre2(newGenre2);
            }
            if (newGenre3 != null){
                genres.setGenre3(newGenre3);
            }
            genrePreferencesRepository.save(genres);
            return "Edit : Success";
        }
    }

    @DeleteMapping(path = "/userGenres/delete/{id}")
    String deleteGenres(@PathVariable int id) {
        genrePreferencesRepository.deleteById(id);
        return "Delete : Success";
    }

}

