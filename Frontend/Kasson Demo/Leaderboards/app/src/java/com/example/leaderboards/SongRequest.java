package com.example.leaderboards;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SongRequest extends AppCompatActivity {

    private static final String SERVER_URL = "http://10.90.74.200:8080/leaderboard/General"; // Replace with your actual backend URL

    private LinearLayout leaderboardListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songreq);

        leaderboardListLayout = findViewById(R.id.leaderboardListLayout);

        // Fetch leaderboard data when the activity is created
        fetchLeaderboardData();
    }

    // Fetch leaderboard data from the backend
    private void fetchLeaderboardData() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseLeaderboardResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SongRequest.this, "Failed to load leaderboard", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Adding request to the request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    // Parse the leaderboard response and display the top 10 songs
    private void parseLeaderboardResponse(String response) {
        try {
            // Assuming the backend sends a list of leaderboard entries in JSON format
            JSONArray leaderboardArray = new JSONArray(response);
            leaderboardListLayout.removeAllViews();  // Clear previous data

            for (int i = 0; i < leaderboardArray.length(); i++) {
                JSONObject leaderboardEntry = leaderboardArray.getJSONObject(i);

                // Get leaderboard data
                String songTitle = leaderboardEntry.getString("songTitle");
                String artist = leaderboardEntry.getString("artist");
                int rank = leaderboardEntry.getInt("rank");

                // Create a TextView to display each leaderboard entry
                TextView leaderboardEntryView = new TextView(this);
                leaderboardEntryView.setText("Rank " + rank + ": " + songTitle + " by " + artist);
                leaderboardEntryView.setTextColor(getResources().getColor(android.R.color.white));
                leaderboardEntryView.setPadding(16, 16, 16, 16);

                // Add the leaderboard entry to the layout
                leaderboardListLayout.addView(leaderboardEntryView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(SongRequest.this, "Error parsing leaderboard data", Toast.LENGTH_SHORT).show();
        }
    }
}
