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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText songInput;
    private Button searchButton;
    private TextView resultView;

    private static final String API_BASE_URL = "https://api.deezer.com/search/track?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songInput = findViewById(R.id.songInput);
        searchButton = findViewById(R.id.searchButton);
        resultView = findViewById(R.id.resultView);

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
            StringBuilder results = new StringBuilder();
            for (DeezerResponse.Track track : deezerResponse.getData()) {
                results.append("Title: ").append(track.getTitle()).append("\n")
                        .append("Artist: ").append(track.getArtist()).append("\n")
                        .append("Album: ").append(track.getAlbum()).append("\n")
                        .append("Cover: ").append(track.getCover()).append("\n\n");
            }
            resultView.setText(results.toString());
        } catch (JsonSyntaxException e) {
            Log.e("JSON Error", e.toString());
            resultView.setText("Error parsing response");
        }
    }
}