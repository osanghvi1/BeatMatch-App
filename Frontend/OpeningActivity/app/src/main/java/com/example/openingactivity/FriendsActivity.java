package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendsActivity extends AppCompatActivity implements Request {
    // UI elements
    private float currentRotation = 0f;
    Button buttonBack, buttonFindFriends;
    ImageButton buttonRefreshFriends;
    ListView lvFriends;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        buttonBack = findViewById(R.id.button_friends_back);
        buttonFindFriends = findViewById(R.id.button_find_friends);
        buttonRefreshFriends = findViewById(R.id.button_refresh_friends);
        lvFriends = findViewById(R.id.friends_list_view);

        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonFindFriends.setOnClickListener(v -> {
            // Handle friend finder button click
            Intent myIntent = new Intent(this, FriendFinderActivity.class);
            startActivity(myIntent);
        });

        buttonRefreshFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRotation += 360f;
                buttonRefreshFriends.animate().rotation(currentRotation).setDuration(800).start();
                // Refresh list of friends
                // TODO will just redo the GET friends task and repopulate the array adapter
                // rotate the image 360 degrees

            }
        });

        // Fetch friends list from the server

        String[] friendsList = {"Friend 1", "Friend 2", "Friend 3", "Friend 4", "Friend 5", "Friend 6", "Friend 7", "Friend 8", "Friend 9", "Friend 10", "Friend 11", "Friend 12", "Friend 13", "Friend 14", "Friend 15", "Friend 16", "Friend 17", "Friend 18", "Friend 19", "Friend 20"};

        // TODO would love a table where user ids, friend ids, and usernames are stored for easy access,
        // could do a get method to get all friends, would be useful for chatting too.

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsList);
        lvFriends.setAdapter(adapter);
        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FriendsActivity.this, "Friend clicked: " + friendsList[position], Toast.LENGTH_SHORT).show();
            }
        });


    }
}
