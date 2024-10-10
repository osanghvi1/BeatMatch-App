package com.example.openingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenrePreferences extends AppCompatActivity {

    private EditText etPopGenre, etRockGenre, etHipHopGenre;
    private Button btnSaveGenres, btnGoToProfile, btnGoToProfileDebug;
    private List<String> allowedGenres = new ArrayList<>(); // List to hold Deezer genres
    private RequestQueue requestQueue;  // For Volley requests
    private TextView textGetResponse; // Moved initialization here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_preferences);

        etPopGenre = findViewById(R.id.etGenre1);
        etRockGenre = findViewById(R.id.etGenre2);
        etHipHopGenre = findViewById(R.id.etGenre3);
        btnSaveGenres = findViewById(R.id.btnSaveGenres);
        btnGoToProfile = findViewById(R.id.btnGoToProfile);
        btnGoToProfileDebug = findViewById(R.id.btnGoToProfileDebug);
        textGetResponse = findViewById(R.id.text_get_response); // Initialize here

        // Initialize request queue for Volley
        requestQueue = Volley.newRequestQueue(this);

        // Fetch allowed genres from Deezer API
        fetchDeezerGenres();

        btnSaveGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the genres from input fields
                String popGenre = etPopGenre.getText().toString().trim();
                String rockGenre = etRockGenre.getText().toString().trim();
                String hiphopGenre = etHipHopGenre.getText().toString().trim();

                // Debug: Log the input values
                Log.d("GenrePreferences", "Pop Genre: " + popGenre);
                Log.d("GenrePreferences", "Rock Genre: " + rockGenre);
                Log.d("GenrePreferences", "Hip Hop Genre: " + hiphopGenre);

                // Get user information from the user class
                int userID = user.getUserID(); // Assuming user is a valid object
                String email = user.getUserEmail();

                // Validate genres
                if (isValidGenre(popGenre) && isValidGenre(rockGenre) && isValidGenre(hiphopGenre)) {
                    JSONObject genreData = new JSONObject();
                    try {
                        // Add user details to JSON
                        genreData.put("userID", userID);
                        genreData.put("email", email);
                        genreData.put("genre1", popGenre); // Added genre1
                        genreData.put("genre2", rockGenre); // Added genre2
                        genreData.put("genre3", hiphopGenre); // Added genre3
                    } catch (JSONException e) {
                        e.printStackTrace();
                        textGetResponse.setText("Error creating JSON object: " + e.getMessage());
                        return;  // Exit if JSON creation fails
                    }

                    // Debug: Log the JSON data to be sent
                    Log.d("GenrePreferences", "JSON Data: " + genreData.toString());

                    // Send the POST request
                    String url = "http://10.90.74.200:8080/userGenres/create";
                    sendPostRequest(url, genreData); // Send the JSON data
                } else {
                    Toast.makeText(GenrePreferences.this, "Please enter valid genres!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenrePreferences.this, ProfilePreferences.class);
                startActivity(intent);
            }
        });

        btnGoToProfileDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenrePreferences.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to send POST request
    private void sendPostRequest(String url, JSONObject jsonData) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("POST RESPONSE", response.toString());

                        try {
                            // Validate response structure before accessing fields
                            if (response.has("message") && response.has("status")) {
                                String message = response.getString("message");  // Get message from response
                                String status = response.getString("status");    // Example: status field in response

                                // Update the TextView with the message from the response
                                textGetResponse.setText("Response: " + message + "\nStatus: " + status);

                                // Check if the response indicates success
                                if ("success".equalsIgnoreCase(status)) {
                                    // Proceed to the next activity if successful
                                    Intent intent = new Intent(GenrePreferences.this, ProfilePreferences.class);
                                    startActivity(intent);
                                }
                            } else {
                                textGetResponse.setText("Unexpected response format");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textGetResponse.setText("Error parsing response: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POST ERROR", error.toString());
                error.printStackTrace();

                // Optionally, show the error in the TextView
                textGetResponse.setText("Error: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    // Method to fetch Deezer genres
    private void fetchDeezerGenres() {
        String deezerGenreUrl = "https://api.deezer.com/genre";

        JsonObjectRequest genreRequest = new JsonObjectRequest(Request.Method.GET, deezerGenreUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray genres = response.getJSONArray("data");
                            for (int i = 0; i < genres.length(); i++) {
                                JSONObject genre = genres.getJSONObject(i);
                                allowedGenres.add(genre.getString("name"));  // Add genre name to the allowed list
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(GenrePreferences.this, "Error fetching genres", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GenrePreferences.this, "Failed to fetch Deezer genres", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add request to the Volley queue
        requestQueue.add(genreRequest);
    }

    // Check if a genre is valid by comparing it to the Deezer genre list
    private boolean isValidGenre(String genre) {
        return allowedGenres.contains(genre.trim());
    }
}

