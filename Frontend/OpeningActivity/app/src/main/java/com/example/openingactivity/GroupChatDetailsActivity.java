package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupChatDetailsActivity extends AppCompatActivity {

    private ListView messagesListView, friendsListView;
    private EditText messageInput;
    private Button sendButton, addFriendsButton;
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayAdapter<String> messagesAdapter, friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat_details);

        String groupName = getIntent().getStringExtra("groupName");

        messagesListView = findViewById(R.id.messagesListView);
        friendsListView = findViewById(R.id.friendsListView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        addFriendsButton = findViewById(R.id.addFriendsButton);
        Button backButton = findViewById(R.id.backButton);

        // Dummy data
        messages.add("Welcome to " + groupName);
        friends.add("user 70");

        messagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        friendsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, friends);

        messagesListView.setAdapter(messagesAdapter);
        friendsListView.setAdapter(friendsAdapter);
        friendsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                messages.add(message);
                messagesAdapter.notifyDataSetChanged();
                messageInput.setText("");
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        addFriendsButton.setOnClickListener(v -> {
            // Logic to add selected friends
            Toast.makeText(this, "Friends added to " + groupName, Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> finish());
    }
}
