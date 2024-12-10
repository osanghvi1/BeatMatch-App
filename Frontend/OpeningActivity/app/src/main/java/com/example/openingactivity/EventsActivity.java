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

        buttonAddEvent.setOnClickListener(v -> {
            // Handle add event button click
            // Create a popup menu or dialog to allow the user to add a new event
        });
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Sets the event info for the events
     * Will have a GET method here to pull all events from the server
     * for now it has 6 dummy events
     */
    private void setEventInfo() {
        // Dummy Images to choose from
        Drawable thumbnail1 = ContextCompat.getDrawable(this, R.mipmap.ic_concert1_foreground);
        Drawable thumbnail2 = ContextCompat.getDrawable(this, R.mipmap.ic_concert2_foreground);
        Drawable thumbnail3 = ContextCompat.getDrawable(this, R.mipmap.ic_concert3_foreground);
        Drawable thumbnail4 = ContextCompat.getDrawable(this, R.mipmap.ic_concert4_foreground);
        Drawable thumbnail5 = ContextCompat.getDrawable(this, R.mipmap.ic_concert5_foreground);
        Drawable thumbnail6 = ContextCompat.getDrawable(this, R.mipmap.ic_concert6_foreground);

        // Dummy Events
        eventList.add(new Event("Windy City Music", "Kanye West", "Chicago, IL", "Jan 3 - 4, 2023", thumbnail1, 10));
        eventList.add(new Event("Pablo's Epic Music", "Pablo Picasso", "Venice, Italy", "Jun 5, 2024", thumbnail2, 20));
        eventList.add(new Event("DJ DRAMA HUB", "DJ DRAMA", "Palm Springs, FL", "May 10, 2024", thumbnail3, 45));
        eventList.add(new Event("Lalapalooza 2024", "Good Morning America", "New York, NY", "July 15, 2025", thumbnail4, 15));
        eventList.add(new Event("Kasson's bash", "Kasson, The Creator", "Omaha, NE", "Jan 31, 2025", thumbnail5, 35));
        eventList.add(new Event("Om's Music Festival", "DJ OM", "Om's house", "Feb 15, 2025", thumbnail6, 70));





    }
}