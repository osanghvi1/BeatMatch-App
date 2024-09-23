package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    private TextView numberTxt; // define number textview variable
    private Button increaseBtn; // define increase button variable
    private Button decreaseBtn; // define decrease button variable
    private Button backBtn;     // define back button variable
    private Button settingsBtn;

    private Button increaseBtnadd10; // EXPERIMENT CHANGE
    private Button decreaseBtnSub10;

    private int counter = 0;    // counter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        /* initialize UI elements */
        numberTxt = findViewById(R.id.number);
        increaseBtn = findViewById(R.id.counter_increase_btn);
        decreaseBtn = findViewById(R.id.counter_decrease_btn);
        backBtn = findViewById(R.id.counter_back_btn);

        //EXPERIMENT CHANGE
        increaseBtnadd10 = findViewById(R.id.counter_increase_btn_add10);
        decreaseBtnSub10 = findViewById(R.id.counter_decrease_btn_Sub10);
        settingsBtn = findViewById(R.id.counter_settings_button);

        /* when increase btn is pressed, counter++, reset number textview */
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(++counter));
            }
        });

        //EXPERIMENT CHANGE
        /* when increase 10 btn is pressed, counter+=10, reset number textview*/
        increaseBtnadd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { numberTxt.setText(String.valueOf(counter+=10)); }
        });

        // when decrease -10 btn is pressed, counter-=10, reset number textview
        decreaseBtnSub10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { numberTxt.setText(String.valueOf(counter-=10)); }
        });

        /* when decrease btn is pressed, counter--, reset number textview */
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(--counter));
            }
        });

        /* when back btn is pressed, switch back to MainActivity */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this, MainActivity.class);
                intent.putExtra("NUM", String.valueOf(counter));  // key-value to pass to the MainActivity
                startActivity(intent);
            }
        });

        /* when back btn is pressed, switch back to MainActivity */
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}