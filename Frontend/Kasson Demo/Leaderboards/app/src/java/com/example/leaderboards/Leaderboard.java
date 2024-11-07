package com.example.leaderboards;

import java.time.LocalDateTime;

public class Leaderboard {

    private Long leaderboardID;
    private String category;
    private Long userID;
    private Long songID;
    private int rank;
    private LocalDateTime updatedTime;

    // Getters and setters
    public Long getLeaderboardID() {
        return leaderboardID;
    }

    public void setLeaderboardID(Long leaderboardID) {
        this.leaderboardID = leaderboardID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getSongID() {
        return songID;
    }

    public void setSongID(Long songID) {
        this.songID = songID;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
