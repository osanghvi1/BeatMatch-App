package com.example.leaderboards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.SongViewHolder> {

    private List<SongL> songList;
    private final Context context;

    public LeaderboardAdapter(Context context, List<SongL> songList) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        SongL song = songList.get(position);
        holder.songTitle.setText(song.getSongTitle());
        holder.rank.setText("Rank: " + song.getRank());

        holder.likeButton.setOnClickListener(v -> {
            long userID = 12345; // Example user ID
            String category = "Top 10";
            sendLikeToBackend(userID, song.getSongID(), song.getRank(), category);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;
        TextView rank;
        Button likeButton;

        public SongViewHolder(View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            rank = itemView.findViewById(R.id.rank);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }

    private void sendLikeToBackend(long userID, long songID, int rank, String category) {
        String url = "http://your-backend-server.com/api/leaderboard/like";

        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("userID", userID);
            jsonRequest.put("songID", songID);
            jsonRequest.put("rank", rank);
            jsonRequest.put("category", category);
            jsonRequest.put("updatedTime", System.currentTimeMillis());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    response -> Toast.makeText(context, "Song liked successfully!", Toast.LENGTH_SHORT).show(),
                    error -> Toast.makeText(context, "Failed to like song", Toast.LENGTH_SHORT).show()
            );

            VolleySingleton.getInstance(context).addToRequestQueue(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
