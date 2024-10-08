package com.example.openingactivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    final int USER_ID = LoginActivity.USER_ID;

    TextView textGetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Set up edge-to-edge display.
        EdgeToEdge.enable(this);
        // Set up the back button.

        textGetUser = findViewById(R.id.text_get_user);
        textGetUser.setText(String.valueOf(USER_ID));
    }
}
