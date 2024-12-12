package com.example.openingactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetailActivity extends AppCompatActivity {
    private List<String> songs;
    private ArrayAdapter<String> adapter;
    private String playlistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        ListView songsListView = findViewById(R.id.songsListView);
        Button addSongButton = findViewById(R.id.addSongButton);
        Button backButton = findViewById(R.id.backButton);

        // Retrieve playlist name and songs from intent
        playlistName = getIntent().getStringExtra("playlistName");
        songs = getIntent().getStringArrayListExtra("songs");

        // Set up the adapter to display songs
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        songsListView.setAdapter(adapter);

        // Listener for the Add Song button
        addSongButton.setOnClickListener(v -> showAddSongDialog());

        // Listener for the Back button
        backButton.setOnClickListener(v -> finish());
    }

    // Show a dialog to add a new song to the playlist
    private void showAddSongDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Song to " + playlistName);

        // Inflate custom view for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_song, null);
        builder.setView(dialogView);

        EditText songNameInput = dialogView.findViewById(R.id.songNameInput);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String songName = songNameInput.getText().toString().trim();
            if (!TextUtils.isEmpty(songName)) {
                songs.add(songName);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Song \"" + songName + "\" added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Song name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
