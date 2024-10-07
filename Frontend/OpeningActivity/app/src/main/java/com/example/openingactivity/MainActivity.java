package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button Login_Button, Signup_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Login_Button = findViewById(R.id.log_in_button);
        Signup_Button = findViewById(R.id.sign_up_button);

        Login_Button.setOnClickListener(v -> {
            // Handle login button click
            Intent myIntent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(myIntent);
        });

        Signup_Button.setOnClickListener(v -> {
            // Handle signup button click
            Intent myIntent = new Intent(this, SignupActivity.class);
            finish();
            startActivity(myIntent);
        });

    }
}