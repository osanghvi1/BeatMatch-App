package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class MainActivity extends AppCompatActivity {

    private EditText genreInput;
    private Button suggestButton;
    private TextView resultView;

    private static final String API_BASE_URL = "https://api.deezer.com/genre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genreInput = findViewById(R.id.genreInput);
        suggestButton = findViewById(R.id.suggestButton);
        resultView = findViewById(R.id.resultView);

        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestGenres();
            }
        });
    }

    private void suggestGenres() {
        fetchTopGenres();
        String genreName = genreInput.getText().toString().trim();
        if (genreName.isEmpty()) {
            resultView.setText("Please enter a genre.");
            return;
        }

        // Step 1: Fetch genre ID based on genre name
        fetchGenreId(genreName);
    }

    private void fetchTopGenres() {
        String searchUrl = "https://api.deezer.com/genre";
//        String postmanUrl = "https://70cbb334-5f5c-48bf-9027-038ff7c7b395.mock.pstmn.io/getGenre";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
//                postmanUrl,
                searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Charts Response", response);
                        parseResponse(response); // Parse the charts response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        resultView.setText("Error: " + error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void fetchGenreId(String genreName) {
        String searchUrl = "https://api.deezer.com/genre"; // Endpoint for genres

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Genres Response", response);
                        // Parse to find the ID for the given genreName
                        parseGenreId(response, genreName);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        resultView.setText("Error: " + error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void parseGenreId(String response, String genreName) {
        Gson gson = new Gson();
        GenreList genreList = gson.fromJson(response, GenreList.class); // Create a class to hold the genre list response

        for (Genre genre : genreList.getData()) {
            if (genre.getName().equalsIgnoreCase(genreName)) {
                String genreId = genre.getId(); // Assuming you have an ID field in your Genre class
                suggestRelatedGenres(genreId); // Now fetch related genres
                return;
            }
        }

        resultView.setText("Genre not found.");
    }

    private void suggestRelatedGenres(String genreId) {
        String searchUrl = API_BASE_URL + genreId + "/related";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Deezer Related Response", response);
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        resultView.setText("Error: " + error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void parseResponse(String response) {
        Log.d("Parsed Response", response);
        Gson gson = new Gson();
        String inputGenreName = genreInput.getText().toString().trim().toLowerCase();
        StringBuilder similarGenres = new StringBuilder();
        int count = 0; // Counter for the number of similar genres found

        try {
            GenreResponse genreResponse = gson.fromJson(response, GenreResponse.class);

            // Check if genreResponse is valid and has data
            if (genreResponse != null && genreResponse.getData() != null && !genreResponse.getData().isEmpty()) {
                for (Genre genre : genreResponse.getData()) {
                    // Ensure that we don't include the input genre itself or the genre "All"
                    if (!genre.getName().equalsIgnoreCase(inputGenreName) && !genre.getName().equalsIgnoreCase("All")) {
                        similarGenres.append(genre.getName()).append(", ");
                        count++;
                        // Stop after getting 3 genres
                        if (count >= 3) {
                            break;
                        }
                    }
                }

                if (similarGenres.length() > 0) {
                    // Remove the trailing comma and space
                    similarGenres.setLength(similarGenres.length() - 2);
                    resultView.setText("Top 3 Similar Genres: " + similarGenres.toString());
                } else {
                    resultView.setText("No matching similar genres found.");
                }
            }

        } catch (JsonSyntaxException e) {
            Log.e("JSON Error", e.toString());
            resultView.setText("Error parsing response");
        }
    }
}