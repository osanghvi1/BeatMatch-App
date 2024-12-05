package database;


import static org.junit.jupiter.api.Assertions.assertEquals;

import database.GenrePreferences.GenrePreferences;
import database.GenrePreferences.GenrePreferencesRepository;
import database.SimilarGenres.SimilarGenresRepository;
import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import database.SimilarGenres.SimilarGenres;
import database.User.User;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class LawsonSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    private GenrePreferencesRepository genrePreferencesRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimilarGenresRepository similarGenresRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    //test ideas:
    //get a user for the user and password parameter would be good

    //main idea is convering a test for each part of crudl (without the l)


    //post something to similar genres and try to check it
    @Test
    public void createSimilarGenresTest() {
        SimilarGenres similarGenres = new SimilarGenres("Metal", "Screamo", "Rock Metal", "rock");

        // Send request and receive response
        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(similarGenres).
                when().
                post("/similarGenres/create/72");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        if(!(returnString.contains("Creation : Success w/ id 7"))) {
            Exception e = new Exception();
            e.printStackTrace();
        }

        similarGenresRepository.deleteByGenreName("Metal");
    }

    //edit something and then get it to see if its changed (could edit a user's genres)
    @Test
    public void editGenrePreferencesTest() {
        GenrePreferences genrePreferences = genrePreferencesRepository.findById(67);

        // Send request and receive response
        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(genrePreferences).
                when().
                put("/userGenres/edit/67");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        if(!(returnString.contains("Edit : Success"))) {
            Exception e = new Exception();
            e.printStackTrace();
        }
    }

    //delete something and check to see if it was deleted
    @Test
    public void deleteUserTest() {
        User user = new User("delete", "user", "deletingUser@iastate.edu", "deletingUser", "password", 1, 1);
        userRepository.save(user);
        int userId = user.getUserID();
        // Send request and receive response
        Response response = RestAssured.given().
                when().
                delete("/users/delete/" + userId);

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        if(!(returnString.contains("User deleted Successfully"))) {
            Exception e = new Exception();
            e.printStackTrace();
        }
    }


    //get a user for the user and password parameter would be good
    @Test
    public void getUserByEmailAndPasswordTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                when().
                get("/users/sbutler@iastate.edu/password");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        if(!(returnString.contains("71"))) {
            Exception e = new Exception();
            e.printStackTrace();
        }
    }

}
