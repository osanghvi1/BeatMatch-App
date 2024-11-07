package com.example.leaderboards;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class SongViewHolder extends RecyclerView.ViewHolder {
    TextView songTitle;
    TextView rank;
    Button likeButton;

    public SongViewHolder(View itemView) {
        super(itemView);
        songTitle = itemView.findViewById(R.id.songTitle); // Assuming you have a TextView for song title
        rank = itemView.findViewById(R.id.rank); // Assuming you have a TextView for rank
        likeButton = itemView.findViewById(R.id.likeButton); // Assuming you have a Button for the like button
    }
}
