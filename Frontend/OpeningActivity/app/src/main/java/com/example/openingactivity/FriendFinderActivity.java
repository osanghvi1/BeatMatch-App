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

public class FriendFinderActivity extends AppCompatActivity implements Request {

    Button buttonBack;
    ListView lvEveryone;
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_finder);
        buttonBack = findViewById(R.id.button_friends_back);
        lvEveryone = findViewById(R.id.friends_list_view);

        executorService = Executors.newSingleThreadExecutor();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String[] friendsList = {"Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7", "Person 8", "Person 9", "Person 10"};

        // TODO would love a table where user ids, friend ids, and usernames are stored for easy access,
        // could do a get method to get all friends, would be useful for chatting too.

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsList);
        lvEveryone.setAdapter(adapter);
        lvEveryone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle friend click
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String response = sendRequest("POST", "/friends/" + user.getUserID() + "/" + friendsList[position], null);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
    }
}
