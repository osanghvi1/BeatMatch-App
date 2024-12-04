package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SimilarGenres.class, SimilarGenres.class, User.class})
@ExtendWith(SpringExtension.class)
public class LawsonSystemTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    //test ideas:
    //post something and then try to get it to check that it posted (similar genres probably easiset)
    //edit something and then get it to see if its changed (could edit a user's genres)
    //get a user for the user and password parameter would be good
    //delete something and check to see if it was deleted

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
    }





}
