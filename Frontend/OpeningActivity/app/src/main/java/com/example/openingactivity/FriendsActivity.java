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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Friend {
    String username;
    String userID;
    //constructor
    public Friend(String username, String userID) {
        this.username = username;
        this.userID = userID;
    }
}

public class FriendsActivity extends AppCompatActivity implements Request {
    // UI elements
    private float currentRotation = 0f;
    Button buttonBack, buttonFindFriends;
    ImageButton buttonRefreshFriends;
    ListView lvFriends;
    ExecutorService executorService;



    String [] friendsList = {"Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7", "Person 8", "Person 9", "Person 10"};

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
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String response = sendRequest("GET", "/friends/" + user.getUserID(), null);
                        // response is a String of friend usernames?
                        // parse the response into an array of strings

                    }
                });
            }
        });

        // Fetch friends list from the server

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsList);
        lvFriends.setAdapter(adapter);

        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FriendsActivity.this, "Friend clicked: " + friendsList[position], Toast.LENGTH_SHORT).show();
                // TODO go into chat activity
            }
        });
    }

    /*
    //i dont know if this fucking works
    private void addFriendsToListView() {
        // need to GET all friends, then add them to the arrayList
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String response = sendRequest("GET", "/friends/" + user.getUserID(), null); // or some other method to get friends
                // response is a String of friend usernames?
                // parse the response into the arrayList
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String friendUsername = jsonArray.getString(i);
                        friendsList.add(new Friend(friendUsername, ""+i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    */


}
