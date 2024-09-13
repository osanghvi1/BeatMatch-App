package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    private TextView numberTxt; // define number textview variable
    private TextView messageText;
    private TextView SuckText;
    private TextView BestText;
    private Button increaseBtn; // define increase button variable
    private Button decreaseBtn; // define decrease button variable
    private Button backBtn;     // define back button variable
    private Button SuckBtn;
    private Button Bestbtn;

    private int counter = 0;    // counter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        /* initialize UI elements */
        SuckText = findViewById(R.id.Suck);
        BestText = findViewById(R.id.Best);
        messageText = findViewById(R.id.main_msg_txt);
        numberTxt = findViewById(R.id.number);
        increaseBtn = findViewById(R.id.counter_increase_btn);
        decreaseBtn = findViewById(R.id.counter_decrease_btn);
        backBtn = findViewById(R.id.counter_back_btn);
        SuckBtn = findViewById(R.id.Suck);
        Bestbtn = findViewById(R.id.Best);

        /* when increase btn is pressed, counter++, reset number textview */
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(++counter));
            }
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

        SuckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SuckText.setText("We'll try to avoid songs like this for you");

            }
        });

        Bestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        // Code to execute when BestBtn is clicked
                BestText.setText("This song was added to your favorites!");
            }
        });

    }
}