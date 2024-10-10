package com.example.openingactivity;
//http://10.90.74.200:8080;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GenrePreferences extends AppCompatActivity {

    private EditText etPopGenre, etRockGenre, etHipHopGenre;
    private Button btnSaveGenres, btnGoToProfile;
    private Button btnGoToProfileDebug;
    private List<String> allowedGenres = new ArrayList<>(); // List to hold Deezer genres

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
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pop", popGenre);
                    editor.putString("rock", rockGenre);
                    editor.putString("hiphop", hiphopGenre);
                    editor.apply();

                    Toast.makeText(GenrePreferences.this, "Genres saved!", Toast.LENGTH_SHORT).show();
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

        // Add request to Volley queue
        VolleySingleton.getInstance(this).addToRequestQueue(genreRequest);
    }

    // Check if a genre is valid by comparing it to the Deezer genre list
    private boolean isValidGenre(String genre) {
        return allowedGenres.contains(genre.trim());
    }
}