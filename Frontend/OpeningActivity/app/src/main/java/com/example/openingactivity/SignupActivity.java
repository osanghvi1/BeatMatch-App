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




public class SignupActivity extends AppCompatActivity {
    //server IP : 10.90.74.200

    // URLs for GET and POST requests
    //final String GET_URL = "http://10.90.74.200:8080/users";
    //"lport@coms-3090-049.class.las.iastate.edu/users"
    //final String GET_URL = "https://a1f4bd84-ddf6-4c6b-b65c-66c8782172eb.mock.pstmn.io/getUser";
    //"http://10.0.2.2:8080/createUser"
    final String GET_URL = "http://10.90.74.200:8080/users";

    final String POST_URL = "http://10.90.74.200:8080/createUser";
    //"http://coms-3090-049.class.las.iastate.edu:8080/createUser"
    //final String POST_URL = "https://a1f4bd84-ddf6-4c6b-b65c-66c8782172eb.mock.pstmn.io/addUser";

    // UI elements
    EditText inputFirstName, inputLastName, inputEmail, inputUsername, inputPassword, inputPasswordConfirm;
    TextView textGetResponse;
    Button buttonBack, buttonSignup, buttonGet, buttonPost;
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
        buttonPost = findViewById(R.id.button_post);
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
                        sendGetRequest(GET_URL);
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
                    textGetResponse.setText("POST sent");
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
                            sendPostRequest(POST_URL, json.toString());
                        }
                    });
                }
            }
        });
    }

    // Method to send POST Request
    private void sendPostRequest(String urlString, String jsonData) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);


            // Write JSON data to the output stream
            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();


            // Get the response
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }


            conn.disconnect();
            String result = sb.toString();


            // Optionally handle the response from the POST request
            Log.d("POST RESPONSE", result);

            if (!Integer.valueOf(result).equals(-1)) {
                try {
                    new user(Integer.valueOf(result));
                } finally {
                    login();
                }
            } else {
                //failure
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {
        Intent intent = new Intent(SignupActivity.this, ForgotPasswordActivity.class);
        finish();
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor service when activity is destroyed
        executorService.shutdown();
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
            System.out.println(result);
            Log.d("GET RESPONSE", result); // Log the response for debugging


            // Update UI on the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    textGetResponse.setText(result);
                }
            });


        } catch (Exception e) {
            Log.e("GET ERROR", e.getMessage(), e); // Log any errors
            e.printStackTrace();
        }
    }




}
