package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventsActivity extends AppCompatActivity implements Request {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        
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




    }
}