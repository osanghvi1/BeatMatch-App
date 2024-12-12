package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupchatsActivity extends AppCompatActivity {

    private Button createGroupChatButton;
    private ListView groupChatsListView;
    private ArrayAdapter<String> groupChatsAdapter;
    private ArrayList<String> groupChats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchats);

        createGroupChatButton = findViewById(R.id.button_create_groupchat);
        groupChatsListView = findViewById(R.id.groupChatsListView);
        Button backButton = findViewById(R.id.backButton);

        groupChatsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groupChats);
        groupChatsListView.setAdapter(groupChatsAdapter);

        // Dummy data
        groupChats.add("Study Group");
        groupChats.add("Game Night");
        groupChats.add("Work Chat");
        groupChatsAdapter.notifyDataSetChanged();

        createGroupChatButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GroupchatsActivity.this);
            builder.setTitle("Create Group Chat");

            final EditText groupNameInput = new EditText(GroupchatsActivity.this);
            groupNameInput.setHint("Enter group chat name");
            builder.setView(groupNameInput);

            builder.setPositiveButton("Create", (dialog, which) -> {
                String groupName = groupNameInput.getText().toString();
                if (groupName.isEmpty()) {
                    Toast.makeText(GroupchatsActivity.this, "Group chat name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    groupChats.add(groupName);
                    groupChatsAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });

        groupChatsListView.setOnItemClickListener((parent, view, position, id) -> {
            String groupName = groupChats.get(position);
            Intent intent = new Intent(GroupchatsActivity.this, GroupChatDetailsActivity.class);
            intent.putExtra("groupName", groupName);
            startActivity(intent);
        });

        // Listener for the Back button
        backButton.setOnClickListener(v -> finish());
    }

}
