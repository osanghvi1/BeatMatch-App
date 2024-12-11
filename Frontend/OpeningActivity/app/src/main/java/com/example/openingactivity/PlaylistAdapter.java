package com.example.openingactivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<PlaylistEntry> playlistEntries = new ArrayList<>();

    public void setPlaylistEntries(List<PlaylistEntry> entries) {
        this.playlistEntries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        PlaylistEntry entry = playlistEntries.get(position);
        holder.playlistName.setText(entry.getPlaylistName());
        holder.songId.setText(String.valueOf(entry.getSongEntryId()));
    }

    @Override
    public int getItemCount() {
        return playlistEntries.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlistName, songId;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlistName);
            songId = itemView.findViewById(R.id.songId);
        }
    }
}

