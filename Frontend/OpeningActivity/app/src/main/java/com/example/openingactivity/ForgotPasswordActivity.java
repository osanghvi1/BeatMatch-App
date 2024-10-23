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


/*
 * Forgot Password Activity - This code runs ONCE on account creation.
 * User will enter their security questions and email (for now until om fixes his table)
 */
public class ForgotPasswordActivity extends AppCompatActivity implements Request {
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
                    //sendPostRequest(POST_URL + "/forgetPassword" , json.toString()); // old method
                    String result = sendRequest("POST", "/forgetPassword", json.toString());
                }
            });

            //Change intent to GenrePreferences
            Intent intent = new Intent(ForgotPasswordActivity.this, GenrePreferences.class);
            finish();
            startActivity(intent);
        });
    }
}
