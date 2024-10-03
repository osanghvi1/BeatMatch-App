package com.example.openingactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupActivity extends AppCompatActivity {


    EditText inputFirstName, inputLastName, inputEmail;
    EditText inputPassword, inputPasswordConfirm;
    TextView textGetResponse;
    Button buttonGet, buttonPost;
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        textGetResponse = findViewById(R.id.text_get_response);
        buttonGet = findViewById(R.id.button_get);
        buttonPost = findViewById(R.id.button_post);
        inputPassword = findViewById(R.id.input_password);
        inputPasswordConfirm = findViewById(R.id.input_password_confirm);


        // Initialize the ExecutorService with a single thread pool
        executorService = Executors.newSingleThreadExecutor();


        // Button to send GET request
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        sendGetRequest("https://a1f4bd84-ddf6-4c6b-b65c-66c8782172eb.mock.pstmn.io/getUser");
                    } //"http://10.0.2.2:8080/mytestapi"
                });
            }
        });


        // Button to send POST request
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = inputFirstName.getText().toString();
                String lastName = inputLastName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String passwordConfirm = inputPasswordConfirm.getText().toString();

                if (!password.equals(passwordConfirm)) {
                    // Handle password mismatch
                    textGetResponse.setText("Passwords do not match");
                    inputPassword.setTextColor(android.graphics.Color.RED);
                    return;
                } else {
                    textGetResponse.setText("Response will appear here");


                    // Create JSON object for POST request
                    JSONObject json = new JSONObject();
                    try {
                        json.put("id", 1); // Example static ID
                        json.put("firstName", firstName);
                        json.put("lastName", lastName);
                        json.put("email", email);
                        json.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            sendPostRequest("https://a1f4bd84-ddf6-4c6b-b65c-66c8782172eb.mock.pstmn.io/addUser", json.toString()); //"http://10.0.2.2:8080/createUser"
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
            os.write(jsonData.getBytes("UTF-8"));
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


        } catch (Exception e) {
            e.printStackTrace();
        }
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
