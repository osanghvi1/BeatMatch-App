package com.example.openingactivity;

import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    final String GET_URL = "http://10.90.74.200:8080";

    public static int USER_ID;

    Button buttonBack, buttonLogin, buttonForgotPassword;
    EditText inputEmail, inputPassword;
    TextView textView, textGetResponse;
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the buttons and edit texts
        buttonBack = findViewById(R.id.login_back_button);
        buttonLogin = findViewById(R.id.button_login);
        buttonForgotPassword = findViewById(R.id.button_forgot_password);
        inputEmail = findViewById(R.id.login_input_username);
        inputPassword = findViewById(R.id.login_input_password);
        textView = findViewById(R.id.textView2);
        textGetResponse = findViewById(R.id.text_get_response);


        executorService = Executors.newSingleThreadExecutor();

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
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // Handle the login button click

                if (!email.isEmpty() && !password.isEmpty()) {
                    // Send GET request
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            sendGetRequest(GET_URL + "/users/" + email + "/" + password);
                        }
                    });

                    //TODO
                    //user class?
                    //

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
        inputEmail.setOnKeyListener(new View.OnKeyListener() {
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

    // Method to send GET Request
    private void sendGetRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }


            in.close();
            urlConnection.disconnect();


            String result = content.toString();
            Log.d("GET RESPONSE", result); // Log the response for debugging


            // Update UI on the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    textGetResponse.setText(result);
                }
            });

            if (result.contains("Success")) {
                login();
            } else {
                textView.setText("Incorrect username or password");
            }


        } catch (Exception e) {
            Log.e("GET ERROR", e.getMessage(), e); // Log any errors
            e.printStackTrace();
        }
    }

    private void login() {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        startActivity(myIntent);
    }


}
