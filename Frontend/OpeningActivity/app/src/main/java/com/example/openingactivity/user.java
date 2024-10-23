package com.example.openingactivity;

public class user {

    private static int userID;
    private static String userEmail;


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
