package com.example.openingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfilePreferences extends AppCompatActivity {

    private EditText etPop;
    private EditText etRock;
    private EditText etHipHop;
    private Button btnUpdate;
    private TextView tvMessage;
    private Button btnBack;
    private List<String> allowedGenres = new ArrayList<>(); // List to hold Deezer genres
    private String updateUrl = "http://10.90.74.200:8080/updateGenres"; // Replace with your server URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_preferences);

        // Initialize views
        etPop = findViewById(R.id.etPop);
        etRock = findViewById(R.id.etRock);
        etHipHop = findViewById(R.id.etHipHop);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvMessage = findViewById(R.id.tvMessage);
        btnBack = findViewById(R.id.btnBack);

        // Load saved genres from SharedPreferences
        loadGenres();

        // Fetch allowed genres from Deezer API
        fetchDeezerGenres();

        // Handle update button click
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGenres();
            }
        });

        // Handle back button click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Ends the current activity and returns to the previous one
            }
        });
    }

    // Load genres from shared preferences
    private void loadGenres() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        etPop.setText(sharedPreferences.getString("pop", ""));
        etRock.setText(sharedPreferences.getString("rock", ""));
        etHipHop.setText(sharedPreferences.getString("hiphop", ""));
    }

    // Fetch allowed genres from Deezer API
    private void fetchDeezerGenres() {
        String deezerGenreUrl = "https://api.deezer.com/genre";

        JsonObjectRequest genreRequest = new JsonObjectRequest(Request.Method.GET, deezerGenreUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the genres from Deezer API response
                            JSONArray genres = response.getJSONArray("data");
                            for (int i = 0; i < genres.length(); i++) {
                                JSONObject genre = genres.getJSONObject(i);
                                allowedGenres.add(genre.getString("name"));  // Add genre name to list
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ProfilePreferences.this, "Error fetching genres", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfilePreferences.this, "Failed to fetch Deezer genres", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add request to Volley queue
        VolleySingleton.getInstance(this).addToRequestQueue(genreRequest);
    }

    // Method to check if a genre is valid using Deezer's genre list
    private boolean isValidGenre(String genre) {
        return allowedGenres.contains(genre.trim());
    }

    // Method to handle updating of genres
    private void updateGenres() {
        // Get entered genres from EditText fields
        String popGenre = etPop.getText().toString();
        String rockGenre = etRock.getText().toString();
        String hiphopGenre = etHipHop.getText().toString();

        // Validate genres
        if (isValidGenre(popGenre) && isValidGenre(rockGenre) && isValidGenre(hiphopGenre)) {
            // Proceed with sending the PUT request and saving to SharedPreferences
            JSONObject genreData = new JSONObject();
            try {
                genreData.put("pop", popGenre);
                genreData.put("rock", rockGenre);
                genreData.put("hiphop", hiphopGenre);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Send the PUT request
            sendPutRequest(updateUrl, genreData);

            // Save valid genres in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pop", popGenre);
            editor.putString("rock", rockGenre);
            editor.putString("hiphop", hiphopGenre);
            editor.apply();

            Toast.makeText(ProfilePreferences.this, "Genres updated!", Toast.LENGTH_SHORT).show();
            tvMessage.setText("Genres updated successfully!");
        } else {
            Toast.makeText(ProfilePreferences.this, "Please enter valid genres!", Toast.LENGTH_SHORT).show();
            tvMessage.setText("Invalid genre(s) entered.");
        }
    }

    // Method to send the PUT request to update the genres
    private void sendPutRequest(String url, JSONObject jsonData) {
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the "message" and "status" from the JSON response
                            String message = response.getString("message");
                            String status = response.getString("status");

                            // Log response and show message to the user
                            Log.d("PUT RESPONSE", response.toString());
                            tvMessage.setText("Updated Genres: " + message + "\nStatus: " + status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tvMessage.setText("Error parsing response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PUT ERROR", error.toString());
                error.printStackTrace();
                tvMessage.setText("Error: " + error.toString());
            }
        });

        // Add the request to the request queue
        VolleySingleton.getInstance(this).addToRequestQueue(putRequest);
    }
}