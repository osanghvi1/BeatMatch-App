package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    // Server URL
    final String POST_URL = "http://10.90.74.200:8080";

    // UI elements
    EditText securityAnswer1, securityAnswer2, inputEmail;
    Button buttonSubmit;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        executorService = Executors.newSingleThreadExecutor();

        // Map UI elements to XML elements
        inputEmail = findViewById(R.id.input_enter_email);
        securityAnswer1 = findViewById(R.id.input_security_answer_1);
        securityAnswer2 = findViewById(R.id.input_security_answer_2);
        buttonSubmit = findViewById(R.id.button_submit_answers);

        // Button to send INITIAL Forgot Password answers
        buttonSubmit.setOnClickListener(v -> {
            String answer1 = securityAnswer1.getText().toString();
            String answer2 = securityAnswer2.getText().toString();
            String email = inputEmail.getText().toString();



            // Create JSON object for POST request
            JSONObject json = new JSONObject();

            // first name last name email username password visibility status
            try {
                //json.put("id", 100); // Example static ID
                json.put("email", email);
                json.put("ansSecurityQuestion1", answer1);
                json.put("ansSecurityQuestion2", answer2);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //send POST request to server with answer1 and answer2
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    sendPostRequest(POST_URL + "/forgetPassword" , json.toString());
                }
            });

            //Change intent to GenrePreferences
            Intent intent = new Intent(ForgotPasswordActivity.this, ProfileActivity.class);
            finish();
            startActivity(intent);
        });
    }

    private void sendPostRequest(String postUrl, String jsonData) {
        try {
            URL url = new URL(postUrl);
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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
