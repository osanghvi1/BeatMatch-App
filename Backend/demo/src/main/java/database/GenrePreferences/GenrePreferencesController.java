package database.GenrePreferences;

import database.SimilarGenres.SimilarGenres;
import database.User.User;
import database.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenrePreferencesController {

    @Autowired
    private GenrePreferencesRepository genrePreferencesRepository;
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "get all genre preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "genre preferences returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenrePreferences.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = @Content)})
    @GetMapping(path = "/userGenres")
    public List<GenrePreferences> getAllGenrePreferences(){
       return genrePreferencesRepository.findAll();
    }

    @Operation(summary = "get genre preferences by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "genre preferences returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenrePreferences.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid user id",
                    content = @Content)})
    @GetMapping(path = "/userGenres/{id}")
    public GenrePreferences getGenrePreferencesById(@PathVariable int id){
        if(genrePreferencesRepository.findById(id) == null){
            return null;
        }
        return genrePreferencesRepository.findById(id);
    }

    @Operation(summary = "create a user's genre preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "genre preferences created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenrePreferences.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid user id",
                    content = @Content)})
    @PostMapping(path = "/userGenres/create")
    public String createGenrePreferences(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "genre preferences to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GenrePreferences.class),
                    examples = @ExampleObject(value = "{ \"userID\": \"123456\", \"email\": \"lport@iastate.edu\", \"genre1\": \"Pop\", \"genre2\": \"Rap\", \"genre3\": \"Indie\" }")))@RequestBody GenrePreferences genrePreferences){
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

    @Operation(summary = "Edit user genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User genre's edited successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenrePreferences.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid user id",
                    content = @Content)})
    @PutMapping(path = "/userGenres/edit/{id}")
    public String editGenrePreferences(@PathVariable int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "genre preferences to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GenrePreferences.class),
                    examples = @ExampleObject(value = "{ \"userID\": \"123456\", \"email\": \"lport@iastate.edu\", \"genre1\": \"Pop\", \"genre2\": \"Rap\", \"genre3\": \"Indie\" }")))@RequestBody GenrePreferences genrePreferences){
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

    @Operation(summary = "Delete user's genre preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "genre preferences deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenrePreferences.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid user id",
                    content = @Content)})
    @DeleteMapping(path = "/userGenres/delete/{id}")
    public String deleteGenres(@PathVariable int id) {
        genrePreferencesRepository.deleteById(id);
        return "Delete : Success";
    }

}

