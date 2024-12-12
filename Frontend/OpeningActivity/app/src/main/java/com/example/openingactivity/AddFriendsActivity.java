package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {

    private String groupName;
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> allFriends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        groupName = getIntent().getStringExtra("groupName");
        friends = getIntent().getStringArrayListExtra("friends");

        // Get all friends from database or API
        // For now, let's assume we have a list of all friends
        allFriends.add("Friend 1");
        allFriends.add("Friend 2");
        allFriends.add("Friend 3");

        ListView friendsListView = findViewById(R.id.friendsListView);
        ArrayAdapter<String> friendsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, allFriends);
        friendsListView.setAdapter(friendsAdapter);
        friendsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        Button addFriendsButton = findViewById(R.id.addFriendsButton);
        addFriendsButton.setOnClickListener(v -> {
            ArrayList<String> selectedFriends = new ArrayList<>();
            for (int i = 0; i < friendsListView.getCount(); i++) {
                if (friendsListView.isItemChecked(i)) {
                    selectedFriends.add(allFriends.get(i));
                }
            }

            if (selectedFriends.isEmpty()) {
                Toast.makeText(AddFriendsActivity.this, "Select at least one friend", Toast.LENGTH_SHORT).show();
            } else {
                // Add selected friends to the group chat
                friends.addAll(selectedFriends);
                Toast.makeText(AddFriendsActivity.this, "Friends added to group chat", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddFriendsActivity.this, GroupchatsActivity.class);
                intent.putExtra("groupName", groupName);
                intent.putStringArrayListExtra("friends", friends);
                startActivity(intent);
            }
        });
    }
}

