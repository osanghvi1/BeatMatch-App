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
    // TextView for showing server responses
    private TextView textGetResponse = findViewById(R.id.text_get_response);  // TextView to display server response

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

        // Initialize request queue for Volley
        requestQueue = Volley.newRequestQueue(this);

        // Fetch allowed genres from Deezer API
        fetchDeezerGenres();

        btnSaveGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate and save genres
                String popGenre = etPopGenre.getText().toString();
                String rockGenre = etRockGenre.getText().toString();
                String hiphopGenre = etHipHopGenre.getText().toString();

                // Only save genres that are allowed
                if (isValidGenre(popGenre) && isValidGenre(rockGenre) && isValidGenre(hiphopGenre)) {
                    JSONObject genreData = new JSONObject();
                    try {
                        genreData.put("pop", popGenre);  // Correctly store genres as JSON
                        genreData.put("rock", rockGenre);
                        genreData.put("hiphop", hiphopGenre);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Send the POST request
                    String url = "http://10.90.74.200:8080/userGenres/create";  // Replace with your server URL
                    sendPostRequest(url, genreData);
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
                            String message = response.getString("message");  // Get message from response
                            String status = response.getString("status");    // Example: status field in response

                            // Update the TextView with the message from the response
                            textGetResponse.setText("Response: " + message + "\nStatus: " + status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textGetResponse.setText("Error parsing response");
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
