//package com.example.androidexample;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class UserPreferencesActivity extends AppCompatActivity {
//
//    private CheckBox cbPop, cbRock, cbHipHop;
//    private Button btnSavePreferences;
//    private SharedPreferences sharedPreferences;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_preferences);
//
//        cbPop = findViewById(R.id.checkbox_pop);
//        cbRock = findViewById(R.id.checkbox_rock);
//        cbHipHop = findViewById(R.id.checkbox_hiphop);
//        btnSavePreferences = findViewById(R.id.btnSavePreferences);
//
//        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
//
//        // Load existing preferences
//        loadPreferences();
//
//        btnSavePreferences.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                savePreferences();
//            }
//        });
//    }
//
//    private void loadPreferences() {
//        cbPop.setChecked(sharedPreferences.getBoolean("pop", false));
//        cbRock.setChecked(sharedPreferences.getBoolean("rock", false));
//        cbHipHop.setChecked(sharedPreferences.getBoolean("hiphop", false));
//    }
//
//    private void savePreferences() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("pop", cbPop.isChecked());
//        editor.putBoolean("rock", cbRock.isChecked());
//        editor.putBoolean("hiphop", cbHipHop.isChecked());
//        editor.apply();
//    }
//}
//
