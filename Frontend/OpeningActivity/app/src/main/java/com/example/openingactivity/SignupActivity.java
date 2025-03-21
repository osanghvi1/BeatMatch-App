package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class SignupActivity extends AppCompatActivity implements Request {
    EditText inputFirstName, inputLastName, inputEmail, inputUsername, inputPassword, inputPasswordConfirm;
    TextView textGetResponse;
    Button buttonBack, buttonSignup, buttonGet;
    ExecutorService executorService;

    // Initialize onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Map UI elements to XML elements
        buttonBack = findViewById(R.id.Signup_Back_Button);
        buttonSignup = findViewById(R.id.button_signup);
        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        textGetResponse = findViewById(R.id.text_get_response);
        buttonGet = findViewById(R.id.button_get);
        inputUsername = findViewById(R.id.signup_input_username);
        inputPassword = findViewById(R.id.input_password);
        inputPasswordConfirm = findViewById(R.id.input_password_confirm);


        // Initialize the ExecutorService with a single thread pool
        executorService = Executors.newSingleThreadExecutor();


        // Button to take us back
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button to send GET request
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //sendGetRequest(GET_URL); // old method
                        String result = sendRequest("GET", "/users", null);
                        if (result != null) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    textGetResponse.setText(result);
                                }
                            });
                        } else {
                            // FOR TESTING
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    textGetResponse.setText("Error fetching data");
                                }
                            });
                        }
                    }
                });
            }
        });


        // Button to send POST request
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Take all information typed into Strings
                String firstName = inputFirstName.getText().toString();
                String lastName = inputLastName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String passwordConfirm = inputPasswordConfirm.getText().toString();
                String username = inputUsername.getText().toString();

                if (!password.equals(passwordConfirm)) {
                    // Handle password mismatch
                    textGetResponse.setText("Passwords do not match");
                } else if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || username.isEmpty()){
                    textGetResponse.setText("Please fill in all fields");
                } else {
                    user.setUserEmail(email);

                    // Create JSON object for POST request
                    JSONObject json = new JSONObject();

                    // first name last name email username password visibility status
                    try {
                        //json.put("id", 100); // Example static ID
                        json.put("firstName", firstName);
                        json.put("lastName", lastName);
                        json.put("email", email);
                        json.put("userName", username);
                        json.put("password", password);
                        json.put("accountVisibility", 1); //Example static visibility
                        json.put("accountStatus", 1); //Example static status
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            //sendPostRequest(POST_URL, json.toString()); // old method
                            String response = sendRequest("POST", "/createUser", json.toString());
                            if (response != null) {
                                try {
                                    int userID = Integer.parseInt(response);
                                    new user(Integer.valueOf(userID));

                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textGetResponse.setText("User Created Successfully!");
                                        }
                                    });

                                    login();
                                } catch (NumberFormatException e) {
                                    Log.e("SignupActivity", "Error parsing user ID: " + e.getMessage());
                                    e.printStackTrace();
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textGetResponse.setText("Error Creating User");
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void login() {
        Intent intent = new Intent(SignupActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor service when activity is destroyed
        executorService.shutdown();
    }
}
