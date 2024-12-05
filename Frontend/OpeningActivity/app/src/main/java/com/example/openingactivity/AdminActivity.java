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
    Button button_admin_return, button_admin_delete_user, button_admin_update_user_status, button_admin_force_leaderboard_refresh, button_admin_delete_user_security;
    EditText input_admin_modify_user, input_admin_new_status;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //map XML elements to UI elements
        textViewAdminHeader = findViewById(R.id.textViewAdminHeader);
        button_admin_return = findViewById(R.id.button_admin_return);
        button_admin_delete_user = findViewById(R.id.button_admin_delete_user);
        input_admin_modify_user = findViewById(R.id.textInput_admin_modify_user);
        button_admin_update_user_status = findViewById(R.id.button_admin_update_user_status);
        input_admin_new_status = findViewById(R.id.textInput_admin_new_status);
        button_admin_force_leaderboard_refresh = findViewById(R.id.button_admin_force_leaderboard_refresh);
        button_admin_delete_user_security = findViewById(R.id.button_admin_delete_user_security);


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
         * allows an admin to delete a user from the database
         */
        button_admin_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    deleteUser(ID);
                }catch (Exception e){
                    System.out.println("Invalid ID");
                }
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
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    int newStatus = Integer.parseInt(input_admin_new_status.getText().toString());
                    updateUserStatus(ID, newStatus);
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * updates the user status in the database
             * @param id the users id
             * @param newStatus the users new status (1, 2 or 3) all users default to 1.
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

        /**
         * This code is for the admin settings force leaderboard refresh button
         * Upon pressing the button, the leaderboard will refresh its values to 0?
         */
        button_admin_force_leaderboard_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forceLeaderboardRefresh();
            }

            /**
             * TODO fix this so it actually refreshes leaderboards
             */
            private void forceLeaderboardRefresh() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("GET", "/leaderboard/refresh", null);
                        //log the result
                    }
                });
            }
        });

        /**
         * This code is for the admin settings delete user security button
         * Allows an admin to delete the a users security questions
         */
        button_admin_delete_user_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    deleteUserSecurity(ID);
                }catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * deletes the user security from the database
             * @param id user id youd like to delete the security questions from
             *           TODO this may not work with id, current methods work using user email, will have to test and see
             */
            private void deleteUserSecurity(int id) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("DELETE", "/forgetPassword/" + id, null);
                        //log the result
            }
        });

    }
}
