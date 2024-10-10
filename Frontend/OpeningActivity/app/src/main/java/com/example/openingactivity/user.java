package com.example.openingactivity;

public class user {

    public static int userID;
    public static String userEmail;


    //constructor
    public user(int UserID) {
        this.userID = UserID;
    }


    //getters and setters
    public static int getUserID() {
        return userID;
    }

    public void setUserID(int UserID) {
        this.userID = UserID;
    }

    public static void setUserEmail(String userEmail) {
        user.userEmail = userEmail;
    }

    public static String getUserEmail() {
        return userEmail;
    }


}
