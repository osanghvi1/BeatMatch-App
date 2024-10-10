package com.example.openingactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForgotPassword extends AppCompatActivity {
    final String GET_URL = "http://10.90.74.200:8080";
    Button buttonRequestPassword;
    EditText inputEmail, inputAnswer1, inputAnswer2;
    TextView textGetPassword;

    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        buttonRequestPassword = findViewById(R.id.button_submit_answers);
        inputEmail = findViewById(R.id.input_enter_email);
        inputAnswer1 = findViewById(R.id.input_security_answer_1);
        inputAnswer2 = findViewById(R.id.input_security_answer_2);
        textGetPassword = findViewById(R.id.text_get_password);

        executorService = Executors.newSingleThreadExecutor();


        buttonRequestPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPassword();
            }

            private void requestPassword() {
                String email = inputEmail.getText().toString();
                String answer1 = inputAnswer1.getText().toString();
                String answer2 = inputAnswer2.getText().toString();

                //send GET request to server with email and answers
                //if successful, reveal user password
                //if not successful, show error message

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        sendGetRequest(GET_URL + "/forgetPassword/" + email + "/" + answer1 + "/" + answer2);
                    }
                });

            }
        });
    }

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
                    textGetPassword.setText(result);
                }
            });

            // Handle the response as needed


        } catch (Exception e) {
            Log.e("GET ERROR", e.getMessage(), e); // Log any errors
            e.printStackTrace();
        }
    }
}
