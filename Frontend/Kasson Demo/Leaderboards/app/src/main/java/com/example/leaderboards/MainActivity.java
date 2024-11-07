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
    private List<SongL> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songList = new ArrayList<>();
        leaderboardAdapter = new LeaderboardAdapter(this, songList);
        recyclerView.setAdapter(leaderboardAdapter);

        fetchLeaderboard();
    }

    private void fetchLeaderboard() {
        for (int i = 0; i < 10; i++) {
            long songID = i * 100;
            songList.add(new SongL(songID, "Song " + i, i + 1));
        }
        leaderboardAdapter.notifyDataSetChanged();
    }

    private void sendLikeToBackend(long userID, long songID, int rank, String category) {
        String url = "http://your-server.com/api/leaderboard/like";

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("userID", userID);
            jsonRequest.put("songID", songID);
            jsonRequest.put("rank", rank);
            jsonRequest.put("category", category);
            jsonRequest.put("updatedTime", LocalDateTime.now().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                response -> Toast.makeText(this, "Song liked successfully!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Failed to like song", Toast.LENGTH_SHORT).show()
        );

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
