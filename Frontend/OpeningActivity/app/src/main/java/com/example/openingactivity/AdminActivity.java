package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminActivity extends AppCompatActivity implements Request {


    //XML elements
    TextView textViewAdminHeader;
    Button button_admin_return, button_admin_delete_user, button_admin_update_user_status;
    EditText input_admin_delete_user;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //map XML elements to UI elements
        TextView textViewAdminHeader = findViewById(R.id.textViewAdminHeader);
        Button button_admin_return = findViewById(R.id.button_admin_return);
        Button button_admin_delete_user = findViewById(R.id.button_admin_delete_user);
        EditText input_admin_delete_user = findViewById(R.id.textInput_admin_delete_user);
        Button button_admin_update_user_status = findViewById(R.id.button_admin_update_user_status);
        EditText input_admin_change_user_status = findViewById(R.id.textInput_admin_change_user_status);
        EditText input_admin_new_status = findViewById(R.id.textInput_admin_new_status);

        executorService = Executors.newSingleThreadExecutor();

        /**
         * This code is for the admin settings back button
         * returns the user to the profile page
         */
        button_admin_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        /**
         * This code is for the admin settings delete user button
         */
        button_admin_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = Integer.parseInt(input_admin_delete_user.getText().toString());
                deleteUser(ID);
            }

            /**
             * deletes the user from the database
             */
            private void deleteUser(int userToDeleteID) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //sendDeleteRequest(DEL_URL + "/users/" + user.getUserID()); // old method
                        String result = sendRequest("DELETE", "/users/delete/" + userToDeleteID, null);

                    }
                });
            }
        });

        /**
         * This code is for the admin settings update user status button
         * Allows the admin to update a users status to 1, 2 or 3
         * 1 being general user, 2 being influencer, 3 being admin
         */
        button_admin_update_user_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = Integer.parseInt(input_admin_change_user_status.getText().toString());
                int newStatus = Integer.parseInt(input_admin_new_status.getText().toString());
                updateUserStatus(ID, newStatus);
            }

            /**
             * updates the user status in the database
             * @param id
             * @param newStatus
             */
            private void updateUserStatus(int id, int newStatus) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("PUT", "/users/update/" + id + "/" + newStatus, null);
                        //log the result
                        System.out.println(result);
                    }
                });
            }
        });
    }
}
