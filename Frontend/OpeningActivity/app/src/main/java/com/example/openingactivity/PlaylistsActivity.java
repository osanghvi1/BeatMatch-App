package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistsActivity extends AppCompatActivity {
    private List<String> playlists = new ArrayList<>();
    private Map<String, List<String>> playlistSongs = new HashMap<>();
    private Button createPlaylistButton, viewPlaylistsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        createPlaylistButton = findViewById(R.id.createPlaylistButton);
        viewPlaylistsButton = findViewById(R.id.viewPlaylistsButton);

        // Listener to create a new playlist
        createPlaylistButton.setOnClickListener(v -> showCreatePlaylistDialog());

        // Listener to view existing playlists
        viewPlaylistsButton.setOnClickListener(v -> showPlaylistsDialog());
    }

    // Show a dialog to create a new playlist
    private void showCreatePlaylistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Playlist");

        // Inflate custom view for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_playlist, null);
        builder.setView(dialogView);

        EditText playlistNameInput = dialogView.findViewById(R.id.playlistNameInput);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String playlistName = playlistNameInput.getText().toString().trim();
            if (!TextUtils.isEmpty(playlistName)) {
                playlists.add(playlistName);
                playlistSongs.put(playlistName, new ArrayList<>());
                Toast.makeText(this, "Playlist \"" + playlistName + "\" created!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Playlist name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    // Show a dialog to view playlists
    private void showPlaylistsDialog() {
        if (playlists.isEmpty()) {
            Toast.makeText(this, "No playlists created yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Playlists");

        String[] playlistsArray = playlists.toArray(new String[0]);
        builder.setItems(playlistsArray, (dialog, which) -> {
            String selectedPlaylist = playlistsArray[which];
            openPlaylistActivity(selectedPlaylist);
        });

        builder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    // Open a new activity to view and manage songs in the selected playlist
    private void openPlaylistActivity(String playlistName) {
        Intent intent = new Intent(this, PlaylistDetailActivity.class);
        intent.putExtra("playlistName", playlistName);
        intent.putStringArrayListExtra("songs", new ArrayList<>(playlistSongs.get(playlistName)));
        startActivity(intent);
    }
}
