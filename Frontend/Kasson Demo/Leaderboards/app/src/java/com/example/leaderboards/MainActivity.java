package com.example.leaderboards;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;
    private List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize song list and adapter
        songList = new ArrayList<>();
        leaderboardAdapter = new LeaderboardAdapter(this, songList);
        recyclerView.setAdapter(leaderboardAdapter);

        // Simulate fetching top 10 leaderboard songs (replace with API call)
        fetchLeaderboard();
    }

    private void fetchLeaderboard() {
        // Example of adding top 10 songs to the list
        for (int i = 0; i < 10; i++) {
            long songID = i * 100; // This generates unique IDs for each song, using `long`
            songList.add(new Song(songID, "Song " + i, i + 1)); // Add top 10 songs with ranks
        }

        leaderboardAdapter.notifyDataSetChanged();
    }

    private void sendLikeToBackend(long userID, long songID, int rank, String category) {
        String url = "http://your-server.com/api/leaderboard/like"; // Replace with your backend URL

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("userID", userID);
            jsonRequest.put("songID", songID);
            jsonRequest.put("rank", rank);
            jsonRequest.put("category", category);
            jsonRequest.put("updatedTime", LocalDateTime.now().toString()); // Modify based on your backend format
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                response -> Toast.makeText(this, "Song liked successfully!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Failed to like song", Toast.LENGTH_SHORT).show()
        );

        // Add request to queue (you need to create a VolleySingleton class for managing requests)
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

}
