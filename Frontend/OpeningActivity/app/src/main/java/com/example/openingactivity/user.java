package com.example.openingactivity;

public class user {

    public static int UserID;


    //constructor
    public user(int UserID) {
        this.UserID = UserID;
    }


    //getters and setters
    public static int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

}
