package database.SimilarGenres;

import database.GenrePreferences.GenrePreferences;
import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimilarGenresController {

    @Autowired
    private SimilarGenresRepository similarGenresRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/similarGenres")
    List<SimilarGenres> getAllSimilarGenres(){ return similarGenresRepository.findAll();}

    @GetMapping(path = "/similarGenres/{id}")
    SimilarGenres getSimilarGenresById(@PathVariable int id){ return similarGenresRepository.findById(id);}

    @PostMapping(path = "/similarGenres/create")
    String createSimilarGenres(@RequestBody SimilarGenres similarGenres){
        if(similarGenres == null){
            return "Creation : Failure";
        }
        else{
            similarGenresRepository.save(similarGenres);
            return "Creation : Success w/ id " + similarGenres.getGenreID();
        }
    }

    @PutMapping(path = "/similarGenres/edit/{name}")
    String editSimilarGenres(@PathVariable String name, @RequestBody SimilarGenres similarGenres){
        SimilarGenres genres = similarGenresRepository.findByGenreName(name);
        String newGenre1 = similarGenres.getSimilarGenre1();
        String newGenre2 = similarGenres.getSimilarGenre2();
        String newGenre3 = similarGenres.getSimilarGenre3();


        if (genres == null){
            return "Edit : Failure";
        }
        else{
            if (newGenre1 != null){
                genres.setSimilarGenre1(newGenre1);
            }
            if (newGenre2 != null){
                genres.setSimilarGenre2(newGenre2);
            }
            if (newGenre3 != null){
                genres.setSimilarGenre3(newGenre3);
            }
            similarGenresRepository.save(genres);
            return "Edit : Success";
        }
    }

    @DeleteMapping(path = "/similarGenres/delete/{id}")
    String deleteSimilarGenres(@PathVariable String genreName){
        similarGenresRepository.deleteByGenreName(genreName);
        if(similarGenresRepository.findByGenreName(genreName) == null){
            return "Delete : Success";
        }
        return "Delete : Failure";
    }
}
