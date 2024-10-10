package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    TextView textGetUser, textGetEmail;
    Button deleteButton;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Set up edge-to-edge display.

        textGetUser = findViewById(R.id.text_get_user_ID);
        textGetEmail = findViewById(R.id.text_get_user_email);
        deleteButton = findViewById(R.id.button_delete_account);

        executorService = Executors.newSingleThreadExecutor();


        textGetUser.setText("" + user.getUserID());
        textGetEmail.setText("" + user.getUserEmail());


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO delete account
            }
        });
    }
}
