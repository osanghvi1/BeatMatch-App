package com.example.openingactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    private void loadGenres() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        etPop.setText(sharedPreferences.getString("pop", ""));
        etRock.setText(sharedPreferences.getString("rock", ""));
        etHipHop.setText(sharedPreferences.getString("hiphop", ""));
    }

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

    private void updateGenres() {
        // Get entered genres from EditText fields
        final String popGenre = etPop.getText().toString();
        final String rockGenre = etRock.getText().toString();
        final String hiphopGenre = etHipHop.getText().toString();

        // Only save genres that are allowed
        if (isValidGenre(popGenre) && isValidGenre(rockGenre) && isValidGenre(hiphopGenre)) {
            // Save valid genres in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pop", popGenre);
            editor.putString("rock", rockGenre);
            editor.putString("hiphop", hiphopGenre);
            editor.apply();

            Toast.makeText(ProfilePreferences.this, "Genres saved!", Toast.LENGTH_SHORT).show();
            tvMessage.setText("Genres updated successfully!");
        } else {
            Toast.makeText(ProfilePreferences.this, "Please enter valid genres!", Toast.LENGTH_SHORT).show();
            tvMessage.setText("Invalid genre(s) entered.");
        }
    }

    // Check if a genre is valid by comparing it to the Deezer genre list
    private boolean isValidGenre(String genre) {
        return allowedGenres.contains(genre.trim());
    }
}