package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class FriendFinderActivity extends AppCompatActivity implements Request {

    Button buttonBack, buttonAddFriend;
    EditText inputFriendCode;
    ExecutorService executorService;

    TextView textResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_finder);
        buttonBack = findViewById(R.id.button_friends_back);
        buttonAddFriend = findViewById(R.id.button_add_friend);
        inputFriendCode = findViewById(R.id.input_friend_code);
        textResponse = findViewById(R.id.text_response);

        executorService = Executors.newSingleThreadExecutor();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int friendCode = inputFriendCode.getText().toString().isEmpty() ? 0 : Integer.parseInt(inputFriendCode.getText().toString());
                int userID = user.getUserID();

                JSONObject json = new JSONObject();

                try {
                    json.put("userIDFriends", friendCode);
                    json.put("userID", userID);
                } catch (JSONException e) {
                }


                // send a POST attempt request to friends table
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = sendRequest("POST", "/friends/create", json.toString());
                            textResponse.setText(result);
                        } catch (Exception e) {
                            textResponse.setText("Error: " + e.getMessage());
                        }
                    }
                });
            }
        });




    }
}
