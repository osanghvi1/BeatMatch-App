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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

        // Fetch genres from Deezer API and then validate the input
        fetchDeezerGenres(new GenreCallback() {
            @Override
            public void onGenresFetched(List<String> deezerGenres) {
                if (isValidGenre(popGenre, deezerGenres) && isValidGenre(rockGenre, deezerGenres) && isValidGenre(hiphopGenre, deezerGenres)) {
                    // Proceed with sending the PUT request and saving to SharedPreferences
                    JSONObject genreData = new JSONObject();
                    try {
                        genreData.put(popGenre, etPop.getText().toString());
                        genreData.put(rockGenre, etRock.getText().toString());
                        genreData.put(hiphopGenre, etHipHop.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Send the PUT request
                    sendPutRequest(genreData);

                    // Save valid genres in SharedPreferences (optional, if needed locally)
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(popGenre, etPop.getText().toString());
                    editor.putString(rockGenre, etRock.getText().toString());
                    editor.putString(hiphopGenre, etHipHop.getText().toString());
                    editor.apply();

                    Toast.makeText(ProfilePreferences.this, "Genres saved!", Toast.LENGTH_SHORT).show();
                    tvMessage.setText("Genres updated successfully!");
                } else {
                    Toast.makeText(ProfilePreferences.this, "Please enter valid genres!", Toast.LENGTH_SHORT).show();
                    tvMessage.setText("Invalid genre(s) entered.");
                }
            }

            @Override
            public void onError() {
                Toast.makeText(ProfilePreferences.this, "Failed to fetch genres from Deezer!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch genres from Deezer API
    private void fetchDeezerGenres(final GenreCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.deezer.com/genre");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray genresArray = jsonResponse.getJSONArray("data");

                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < genresArray.length(); i++) {
                        JSONObject genreObject = genresArray.getJSONObject(i);
                        genreList.add(genreObject.getString("name"));
                    }

                    // Callback to return the genres
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onGenresFetched(genreList);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
        }).start();
    }

    // Interface for the Deezer genre callback
    interface GenreCallback {
        void onGenresFetched(List<String> deezerGenres);

        void onError();
    }

    // Method to check if a genre is valid using Deezer's genre list
    private boolean isValidGenre(String genre, List<String> deezerGenres) {
        return deezerGenres.contains(genre.trim());
    }
    int userId = user.getUserID(); // Get the UserID

    // Method to send the PUT request (same as before)
    private void sendPutRequest(final JSONObject genreData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Replace with your actual API endpoint
                    URL url = new URL("http://10.90.74.200:8080/userGenres/update");  // Targeting your server URL
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT"); // Use PUT for update
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    // Create a new JSONObject to hold UserID and genreData
                    JSONObject requestData = new JSONObject();
                    requestData.put("email", user.getUserEmail());
                    requestData.put("userId", ("" + user.getUserID()));  // Add UserID
                    requestData.put("genres", genreData);  // Add genres

                    // Write the combined JSON data to the output stream
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = requestData.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get the response
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }

                        // Handle the response on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProfilePreferences.this, "Genres updated on server!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProfilePreferences.this, "Failed to update genres on server!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}