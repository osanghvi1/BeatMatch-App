package com.example.openingactivity;

import static java.lang.System.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Friend {
    String username;
    int userID;
    //constructor
    public Friend(String username, int userID) {
        this.username = username;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return username;
    }
}

public class FriendsActivity extends AppCompatActivity implements Request {
    // UI elements
    private float currentRotation = 0f;
    Button buttonBack, buttonFindFriends;
    ImageButton buttonRefreshFriends;
    ListView lvFriends;
    ExecutorService executorService;
    ArrayList<Friend> friendsList = new ArrayList<>();
    ArrayAdapter<Friend> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        buttonBack = findViewById(R.id.button_friends_back);
        buttonFindFriends = findViewById(R.id.button_find_friends);
        buttonRefreshFriends = findViewById(R.id.button_refresh_friends);
        Button buttonGroupChat = findViewById(R.id.button_group_chat);

        buttonGroupChat.setOnClickListener(v -> {
            Intent intent = new Intent(FriendsActivity.this, GroupchatsActivity.class);
            ArrayList<String> friendsNames = new ArrayList<>();
            for (Friend friend : friendsList) {
                friendsNames.add(friend.username);
            }
            intent.putStringArrayListExtra("friends", friendsNames);
            startActivity(intent);
        });



        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();

        lvFriends = findViewById(R.id.friends_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, friendsList);
        lvFriends.setAdapter(adapter);


        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend selectedFriend = friendsList.get(position);
                // TODO enter chat with selectedFriend
                Toast.makeText(FriendsActivity.this, "Chatting with " + selectedFriend.username, Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.friends);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.friends) {
                    return true;
                } else if (item.getItemId() == R.id.events) {
                    Intent intent = new Intent(FriendsActivity.this, EventsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.swiping) {
                    Intent intent = new Intent(FriendsActivity.this, SwipingActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.leaderboard) {
                    Intent intent = new Intent(FriendsActivity.this, LeaderboardActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    Intent intent = new Intent(FriendsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // do a GET request from the friends table to get all friends, then add them into the arraylist
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // TESTING DUMMY DATA
                //friendsList.add(new Friend("Person1", 1));
                //friendsList.add(new Friend("Person2", 2));
                //friendsList.add(new Friend("Person3", 3));
                try {
                    String result = sendRequest("GET", "/friends/" + user.getUserID(), null);
                    // TODO ask om to return a sequence of friends
                    //parse the JSON body to return user IDS
                    JSONArray json = new JSONArray(result);

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject friend = json.getJSONObject(i);
                        int userIDFriends = friend.getInt("userIDFriends");
                        String username = friend.getString("userNameFriend");
                        friendsList.add(new Friend((username), userIDFriends));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

        buttonGroupChat.setOnClickListener(v -> {
            Intent intent = new Intent(FriendsActivity.this, GroupchatsActivity.class);
            startActivity(intent);
        });


        buttonRefreshFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friendsList.clear();
                // For button rotation
                currentRotation += 360f;
                buttonRefreshFriends.animate().rotation(currentRotation).setDuration(800).start();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
