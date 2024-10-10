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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
                    JSONObject genreData = new JSONObject();
                    try {
                        genreData.put(popGenre, etPopGenre.getText().toString());
                        genreData.put(rockGenre, etRockGenre.getText().toString());
                        genreData.put(hiphopGenre, etHipHopGenre.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Send the POST request
                    sendPostRequest(genreData);
                } else {
                    Toast.makeText(GenrePreferences.this, "Please enter valid genres!", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Method to send POST request


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


    private void sendPostRequest(final JSONObject genreData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.90.74.200:8080/userGenres/create;");  // Replace with your endpoint
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    // Create a new JSONObject to hold UserID and genreData
                    JSONObject requestData = new JSONObject();
                    requestData.put("userId", user.getUserID());
                    requestData.put("email", user.getUserEmail());
                    requestData.put("genres", genreData);  // Add genres

                    // Write the combined JSON data to the output stream
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = requestData.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get the response
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }

                        // Handle the response on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GenrePreferences.this, "Genres saved!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GenrePreferences.this, "Failed to save genres!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
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