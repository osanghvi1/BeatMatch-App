package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private EditText etPop;
    private EditText etRock;
    private EditText etHipHop;
    private Button btnUpdate;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etPop = findViewById(R.id.etPop);
        etRock = findViewById(R.id.etRock);
        etHipHop = findViewById(R.id.etHipHop);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvMessage = findViewById(R.id.tvMessage);

        // Load saved genres from SharedPreferences
        loadGenres();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGenres();
                tvMessage.setText("Genres updated successfully!");
            }
        });
    }

    private void loadGenres() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        // Load saved genres into the EditText fields
        etPop.setText(sharedPreferences.getString("pop", ""));
        etRock.setText(sharedPreferences.getString("rock", ""));
        etHipHop.setText(sharedPreferences.getString("hiphop", ""));
    }

    private void saveGenres() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pop", etPop.getText().toString());
        editor.putString("rock", etRock.getText().toString());
        editor.putString("hiphop", etHipHop.getText().toString());
        editor.apply();
    }
}