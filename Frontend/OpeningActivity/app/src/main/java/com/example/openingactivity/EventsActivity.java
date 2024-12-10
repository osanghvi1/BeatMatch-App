package com.example.openingactivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity implements Request {
    private ArrayList<Event> eventList;
    private RecyclerView recyclerView;

    private ImageButton buttonAddEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        recyclerView = findViewById(R.id.EVENTVIEW);
        buttonAddEvent = findViewById(R.id.imageButton_event_add);
        eventList = new ArrayList<>();
        
        setEventInfo();
        setAdapter();
        
        /**
         * Bottom navigation bar setup & functionality
         */
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.events);
        /**
         * Functions as the listener for the bottom navigation bar as well as all of the functionality
         * changes activities based on what item is pressed
         */
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.friends) {
                    Intent intent = new Intent(EventsActivity.this, FriendsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.events) {
                    return true;
                } else if (item.getItemId() == R.id.swiping) {
                    Intent intent = new Intent(EventsActivity.this, SwipingActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.leaderboard) {
                    Intent intent = new Intent(EventsActivity.this, LeaderboardActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    Intent intent = new Intent(EventsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        /*buttonAddEvent.setOnClickListener(v -> {
            // Handle add event button click
            Intent myIntent = new Intent(this, AddEventActivity.class);
            startActivity(myIntent);
        });

         */
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setEventInfo() {
        Drawable thumbnail1 = ContextCompat.getDrawable(this, R.drawable.gradient_background3);
        Drawable thumbnail2 = ContextCompat.getDrawable(this, R.drawable.gradient_background4);
        Drawable thumbnail3 = ContextCompat.getDrawable(this, R.drawable.gradient_background2);


        eventList.add(new Event("Event 1", "Host 1", "Location 1", "Date 1", thumbnail1));
        eventList.add(new Event("Event 2", "Host 2", "Location 2", "Date 2", thumbnail2));
        eventList.add(new Event("Event 3", "Host 3", "Location 3", "Date 3", thumbnail3));
        eventList.add(new Event("Event 4", "Host 4", "Location 4", "Date 4", thumbnail1));
        eventList.add(new Event("Event 5", "Host 5", "Location 5", "Date 5", thumbnail2));
        eventList.add(new Event("Event 6", "Host 6", "Location 6", "Date 6", thumbnail3));





    }
}