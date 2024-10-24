package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendsActivity extends AppCompatActivity implements Request {
    // UI elements
    Button buttonBack;
    ListView lvFriends;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        buttonBack = findViewById(R.id.button_friends_back);
        lvFriends = findViewById(R.id.friends_list_view);

        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Fetch friends list from the server

        String[] friendsList = {"Friend 1", "Friend 2", "Friend 3"};

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
