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
                String friendCode = inputFriendCode.getText().toString();
                // send a POST attempt request to friends table
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("POST", "/friends/" + user.getUserID() + "/" + friendCode, null);
                        textResponse.setText(result);
                    }
                });
            }
        });




    }
}
