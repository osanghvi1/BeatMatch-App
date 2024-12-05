package database.SimilarGenres;


import database.GenrePreferences.GenrePreferences;
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
public class SimilarGenresController {

    @Autowired
    private SimilarGenresRepository similarGenresRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "list all similar genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "similar genres listed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimilarGenres.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)})
    @GetMapping(path = "/similarGenres")
    List<SimilarGenres> getAllSimilarGenres(){ return similarGenresRepository.findAll();}


    @Operation(summary = "Get similar genres by Genre id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "genre found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimilarGenres.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid genre id",
                    content = @Content)})
    @GetMapping(path = "/similarGenres/{name}")
    String getSimilarGenresById(@PathVariable String name){
        SimilarGenres similarGenres = similarGenresRepository.findByGenreName(name);
        if (similarGenres == null){
            return "No such genres exists";
        }
        return similarGenres.getSimilarGenre1() + "," + similarGenres.getSimilarGenre2() + "," + similarGenres.getSimilarGenre3();
    }


    @Operation(summary = "create simliar genres by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "genres created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimilarGenres.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid permissions to create",
                    content = @Content)})
    @PostMapping(path = "/similarGenres/create/{uid}")
    String createSimilarGenres(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "similar genre to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SimilarGenres.class),
                    examples = @ExampleObject(value = "{ \"genreName\": \"Rock\", \"similarGenre1\": \"Metal\", \"similarGenre2\": \"Punk Rock\", \"similarGenre3\": \"Emo Rock\" }")))@RequestBody SimilarGenres similarGenres, @PathVariable int uid){
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

    @Operation(summary = "Edit genre by name with id permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully edited genre",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimilarGenres.class)) }),
            @ApiResponse(responseCode = "400", description = "insufficient permissions or invalid genre",
                    content = @Content)})
    @PutMapping(path = "/similarGenres/edit/{uid}/{name}")
    String editSimilarGenres(@PathVariable int uid, @PathVariable String name, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "similar genre to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SimilarGenres.class),
                    examples = @ExampleObject(value = "{ \"genreName\": \"Rock\", \"similarGenre1\": \"Metal\", \"similarGenre2\": \"Punk Rock\", \"similarGenre3\": \"Emo Rock\" }")))@RequestBody SimilarGenres similarGenres){
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


    @Operation(summary = "Delete genre by name with id permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted genre",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimilarGenres.class)) }),
            @ApiResponse(responseCode = "400", description = "invalid genre id or user permissions",
                    content = @Content)})
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
