package com.example.openingactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    Button buttonRequestPassword;
    EditText inputEmail, inputAnswer1, inputAnswer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        buttonRequestPassword = (Button) findViewById(R.id.button_submit_answers);
        inputEmail = (EditText) findViewById(R.id.input_enter_email);
        inputAnswer1 = (EditText) findViewById(R.id.input_security_answer_1);
        inputAnswer2 = (EditText) findViewById(R.id.input_security_answer_2);
    }
}
