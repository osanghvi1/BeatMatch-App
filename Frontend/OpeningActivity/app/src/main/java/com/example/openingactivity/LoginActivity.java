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

    public static int USER_ID;

    Button buttonBack, buttonLogin, buttonForgotPassword;
    EditText inputEmail, inputPassword;
    TextView textView, textGetResponse;


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
                    sendGetRequest(GET_URL + "/user/" + email + "/" + password);  //url/user/email/password
                    //TODO
                    // Switch Activity to ProfileActivity + display user information.
                    // Store user ID for easier lookup in other activities.
                    // global variable? or user class?
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlString, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GET RESPONSE", response.toString());
                        try {
                            // Check if the response contains the "success" key indicating if the user exists or not
                            if (response.has("success")) {
                                // Extract the user information and status from the JSON response
                                //TODO find out to extract user ID from json object, talk to lawson about naming convention
                                JSONObject user = response.getJSONObject("ID");
                                //store the users ID for later use.
                                USER_ID = user.getInt("ID");
                                String firstName = user.getString("firstName");
                                String lastName = user.getString("lastName");
                                String email = user.getString("email");
                                // Update the TextView with the student details and status from the response
                                textGetResponse.setText(
                                        "Student Info:" + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email);
                            } else {
                                // Handle the case when the student doesn't exist (i.e., 404 Not Found)
                                String message = response.getString("message");
                                String status = response.getString("status");
                                textGetResponse.setText("Response: " + message + "\nStatus: " + status);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textGetResponse.setText("Error parsing response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GET ERROR", error.toString());
                error.printStackTrace();
                textGetResponse.setText("Error: " + error.toString());
            }
        });
    }


}
