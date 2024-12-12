package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Initialize and assign variable
        BottomNavigationView menuNavigationView = findViewById(R.id.menu_navigation);

        // Set Leaderboard selected
        menuNavigationView.setSelectedItemId(R.id.leaderboard);

        // Perform item selected listener
        menuNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.friends) {
                    Intent intent = new Intent(LeaderboardActivity.this, FriendsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.events) {
                    Intent intent = new Intent(LeaderboardActivity.this, EventsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.swiping) {
                    Intent intent = new Intent(LeaderboardActivity.this, SwipingActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.leaderboard) {
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    Intent intent = new Intent(LeaderboardActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.leaderboard_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create dummy leaderboard items
        List<LeaderboardItem> leaderboardItems = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            leaderboardItems.add(new LeaderboardItem(i, "Song " + i, 0 + i));
        }

        // Set adapter
        LeaderboardAdapter adapter = new LeaderboardAdapter(leaderboardItems);
        recyclerView.setAdapter(adapter);
    }
}
