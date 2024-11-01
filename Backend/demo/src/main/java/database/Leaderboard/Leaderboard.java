package database.Leaderboard;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaderboardID; // Unique identifier for each leaderboard entry

    @Column(nullable = false)
    private String category; // Category of the leaderboard (e.g., Trending, Top Rap, etc.)

    @Column(nullable = false)
    private Long userID; // ID of the user who liked the song

    @Column(nullable = false)
    private Long songID; // ID of the liked song

    @Column(nullable = false)
    private int rank; // Rank of the song in this category

    @Column(nullable = false)
    private LocalDateTime updatedTime; // Timestamp of the last update

    // Constructors
    public Leaderboard() {}

    public Leaderboard(String category, Long userID, Long songID, int rank, LocalDateTime updatedTime) {
        this.category = category;
        this.userID = userID;
        this.songID = songID;
        this.rank = rank;
        this.updatedTime = updatedTime;
    }

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
