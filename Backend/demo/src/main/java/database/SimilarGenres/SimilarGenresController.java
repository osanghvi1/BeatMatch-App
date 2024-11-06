package database.SimilarGenres;

import database.GenrePreferences.GenrePreferences;
import database.User.User;
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

    @GetMapping(path = "/similarGenres/{name}")
    String getSimilarGenresById(@PathVariable String name){
        SimilarGenres similarGenres = similarGenresRepository.findByGenreName(name);
        if (similarGenres == null){
            return "No such genres exists";
        }
        return similarGenres.getSimilarGenre1() + "," + similarGenres.getSimilarGenre2() + "," + similarGenres.getSimilarGenre3();
    }

    @PostMapping(path = "/similarGenres/create/{uid}")
    String createSimilarGenres(@RequestBody SimilarGenres similarGenres, @PathVariable int uid){
        User user = userRepository.findById(uid);
        int accountStatus = user.getAccountStatus();
        if (accountStatus != 3){ //3 = admin account
            return "Insufficient Permissions to Create Genres";
        }
        else if (similarGenres == null){
            return "Creation : Failure";
        }
        else{
            similarGenresRepository.save(similarGenres);
            return "Creation : Success w/ id " + similarGenres.getGenreID();
        }
    }

    @PutMapping(path = "/similarGenres/edit/{uid}/{name}")
    String editSimilarGenres(@PathVariable int uid, @PathVariable String name, @RequestBody SimilarGenres similarGenres){
        User user = userRepository.findById(uid);
        int accountStatus = user.getAccountStatus();

        //check if the user is an admin
        if (accountStatus != 3){
            return "Insufficient Permissions to Edit Genres";
        }

        SimilarGenres genres = similarGenresRepository.findByGenreName(name);
        if (genres == null){
            return "Edit : Failure";
        }
        else{
            String newGenre1 = similarGenres.getSimilarGenre1();
            String newGenre2 = similarGenres.getSimilarGenre2();
            String newGenre3 = similarGenres.getSimilarGenre3();
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

    @DeleteMapping(path = "/similarGenres/delete/{uid}/{name}")
    String deleteSimilarGenres(@PathVariable int uid, @PathVariable String name){
        User user = userRepository.findById(uid);
        int accountStatus = user.getAccountStatus();
        if (accountStatus != 3){ //3 = admin account
            return "Insufficient Permissions to Delete Genres";
        }

        similarGenresRepository.deleteByGenreName(name);
        if(similarGenresRepository.findByGenreName(name) == null){
            return "Delete : Success";
        }
        return "Delete : Failure";
    }
}
