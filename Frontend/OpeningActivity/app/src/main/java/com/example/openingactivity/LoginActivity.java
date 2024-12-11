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

public class LoginActivity extends AppCompatActivity implements Request {
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
                user.setUserEmail(email);

                // Handle the login button click

                if (!email.isEmpty() && !password.isEmpty()) {
                    // Send GET request
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            //sendGetRequest(GET_URL + "/users/" + email + "/" + password); // old method
                            String result = sendRequest("GET", "/users/" + email + "/" + password, null);
                            if (Integer.parseInt(result) != -1) {
                                try {
                                    int userID = Integer.parseInt(result);
                                    new user(Integer.valueOf(userID));
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textGetResponse.setText("Login Successful!");
                                        }
                                    });
                                    login();
                                } catch (NumberFormatException e) {
                                    Log.e("LoginActivity", "Error parsing user ID: " + e.getMessage());
                                    e.printStackTrace();
                                    // TESTING
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textGetResponse.setText("Error parsing user ID");
                                        }
                                    });
                                }
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        textGetResponse.setText("Incorrect username or password");
                                    }
                                });
                            }
                        }
                    });

                } else {
                    textView.setText("please fill in all fields*");
                }
            }

        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change intent to new forgot password screen
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
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

        inputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    buttonLogin.performClick();
                    return true;
                }
                return false;
            }
        });


    }

    private void login() {
        //Change intent to Profile
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
