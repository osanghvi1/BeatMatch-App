package com.example.openingactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    final String GET_URL = "http://10.90.74.200:8080";

    Button buttonBack, buttonLogin, buttonForgotPassword;
    EditText inputUsername, inputPassword;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the buttons and edit texts
        buttonBack = findViewById(R.id.login_back_button);
        buttonLogin = findViewById(R.id.button_login);
        buttonForgotPassword = findViewById(R.id.button_forgot_password);
        inputUsername = findViewById(R.id.login_input_username);
        inputPassword = findViewById(R.id.login_input_password);
        textView = findViewById(R.id.textView2);

        // Button to take us back
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                // Handle the login button click

                if (!username.isEmpty() && !password.isEmpty()) {
                    // Send GET request
                    sendGetRequest(GET_URL + "/user/" + username);
                } else {
                    textView.setText("please fill in all fields*");
                }
            }

        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the forgot password button click
            }
        });

        //method that sets TextView to nothing when username field is typed in
        inputUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                textView.setText("");
                return false;
            }
        });

        //method that sets TextView to nothing when password field is typed in
        inputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                textView.setText("");
                return false;
            }
        });


    }

    private void sendGetRequest(String s) {
    }


}
