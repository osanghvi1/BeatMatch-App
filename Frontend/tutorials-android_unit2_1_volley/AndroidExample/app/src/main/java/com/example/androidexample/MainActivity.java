package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.Gravity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText songInput;
    private Button searchButton;
    private TextView resultView;
    private LinearLayout songListLayout;

    private static final String API_BASE_URL = "https://api.deezer.com/search/track?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songInput = findViewById(R.id.songInput);
        searchButton = findViewById(R.id.searchButton);
        resultView = findViewById(R.id.resultView);
        songListLayout = findViewById(R.id.songListLayout);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSong();
            }
        });
    }

    private void searchSong() {
        String songTitle = songInput.getText().toString().trim();
        String searchUrl = API_BASE_URL + songTitle;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Deezer Response", response);
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

        // Adding request to the request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void parseResponse(String response) {
        Gson gson = new Gson();
        try {
            DeezerResponse deezerResponse = gson.fromJson(response, DeezerResponse.class);

            // Clear previous results
            songListLayout.removeAllViews();

            for (DeezerResponse.Track track : deezerResponse.getData()) {
                // Create a TextView for each song's details
                TextView songDetails = new TextView(this);
                songDetails.setText("Title: " + track.getTitle() + "\n"
                        + "Artist: " + track.getArtist() + "\n"
                        + "Album: " + track.getAlbum() + "\n"
                        + "Cover: " + track.getCover());
                songDetails.setPadding(16, 16, 16, 16);

                // Create a "Like" button for each song
                Button likeButton = new Button(this);
                likeButton.setText("Like");
                likeButton.setGravity(Gravity.CENTER);

                // Add a listener to the Like button
                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Liked: " + track.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the TextView and Button to the layout
                songListLayout.addView(songDetails);
                songListLayout.addView(likeButton);
            }

        } catch (JsonSyntaxException e) {
            Log.e("JSON Error", e.toString());
            resultView.setText("Error parsing response");
        }
    }
}