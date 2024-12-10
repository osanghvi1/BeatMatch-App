package com.example.openingactivity;

public class LeaderboardItem {
    private int rank;
    private String songTitle;
    private int likes;

    // Constructor
    public LeaderboardItem(int rank, String songTitle, int likes) {
        this.rank = rank;
        this.songTitle = songTitle;
        this.likes = likes;
    }

    public int getRank() {
        return rank;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getLikes() {
        return likes;
    }
}
