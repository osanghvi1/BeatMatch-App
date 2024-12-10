package com.example.openingactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private List<LeaderboardItem> leaderboardItems;

    public LeaderboardAdapter(List<LeaderboardItem> leaderboardItems) {
        this.leaderboardItems = leaderboardItems;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        LeaderboardItem item = leaderboardItems.get(position);
        holder.rankText.setText(String.valueOf(item.getRank()));
        holder.songTitleText.setText(item.getSongTitle());
        holder.likesText.setText(String.valueOf(item.getLikes()));
    }

    @Override
    public int getItemCount() {
        return leaderboardItems.size();
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView rankText, songTitleText, likesText;

        public LeaderboardViewHolder(View itemView) {
            super(itemView);
            rankText = itemView.findViewById(R.id.rank);
            songTitleText = itemView.findViewById(R.id.song_title);
            likesText = itemView.findViewById(R.id.likes);
        }
    }
}
