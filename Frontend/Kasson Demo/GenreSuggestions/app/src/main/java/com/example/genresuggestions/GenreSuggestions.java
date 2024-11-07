package com.example.genresuggestions;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GenreSuggestions extends AppCompatActivity {

    private EditText genreInput;
    private Button suggestButton;
    private TextView resultView;

    // Set the URL to your backend endpoint
    private static final String BACKEND_API_URL = "http://10.90.74.200:8080/similarGenres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
        String genreName = genreInput.getText().toString().trim();
        Log.d("Input Genre", genreName);  // Log the input genre to check

        if (genreName.isEmpty()) {
            resultView.setText("Please enter a genre.");
            return;
        }

        // Reset the result view to clear previous results
        resultView.setText("Loading...");

        // Call backend to get similar genres
        fetchSimilarGenres(genreName);
    }


    private void fetchSimilarGenres(String genreName) {
        // Build the URL to include the genre name as a query parameter
        String searchUrl = BACKEND_API_URL + "?genre=" + genreName;

        // Log the URL to ensure the correct genre is being sent
        Log.d("Search URL", searchUrl);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Backend Response", response);
                        parseResponse(response); // Parse backend response
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
        Log.d("Backend Response", response);  // Log to confirm the response format

        String inputGenreName = genreInput.getText().toString().trim().toLowerCase();  // Get input genre and convert to lowercase
        StringBuilder similarGenres = new StringBuilder();

        try {
            // Split the response string by commas
            String[] genresArray = response.split(",");

            // Check if the array contains genres
            if (genresArray != null && genresArray.length > 0) {
                int count = 0;

                // Loop through the genres and append them to the StringBuilder
                for (String genre : genresArray) {
                    genre = genre.trim(); // Remove any leading or trailing spaces

                    if (!genre.isEmpty() && !genre.equalsIgnoreCase(inputGenreName)) {  // Exclude the input genre
                        if (count > 0) {
                            similarGenres.append(", ");  // Add comma only between genres
                        }
                        similarGenres.append(genre);  // Append the genre
                        count++;

                        // Stop after getting 3 genres
                        if (count >= 5) {
                            break;
                        }
                    }
                }

                // Display the result
                if (count > 0) {
                    resultView.setText("Top 3 Similar Genres: " + similarGenres.toString());
                } else {
                    resultView.setText("No similar genres found.");
                }
            } else {
                resultView.setText("No similar genres found.");
            }

        } catch (Exception e) {
            Log.e("General Error", e.toString());
            resultView.setText("Unexpected error: " + e.getMessage());
        }
    }

}