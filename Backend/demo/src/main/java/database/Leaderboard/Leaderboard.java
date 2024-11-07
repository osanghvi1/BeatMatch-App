package database.Leaderboard;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaderboard_id")
    private Long leaderboardID;

    @Column(name = "song_id", nullable = false)
    private Long songID;

    @Column(name = "rank", nullable = false)
    private int rank;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @Column(name = "category")
    private String category;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    // Default constructor
    public Leaderboard() {}

    // Parameterized constructor
    public Leaderboard(Long songID, int rank, int likeCount, String category, LocalDateTime updatedTime) {
        this.songID = songID;
        this.rank = rank;
        this.likeCount = likeCount;
        this.category = category;
        this.updatedTime = updatedTime;
    }

    // Getters and Setters
    public Long getLeaderboardID() { return leaderboardID; }
    public Long getSongID() { return songID; }
    public int getRank() { return rank; }
    public int getLikeCount() { return likeCount; }
    public String getCategory() { return category; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }

    public void setLeaderboardID(Long leaderboardID) { this.leaderboardID = leaderboardID; }
    public void setSongID(Long songID) { this.songID = songID; }
    public void setRank(int rank) { this.rank = rank; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setCategory(String category) { this.category = category; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }

    @Override
    public String toString() {
        return "Leaderboard{" +
                "leaderboardID=" + leaderboardID +
                ", songID=" + songID +
                ", rank=" + rank +
                ", likeCount=" + likeCount +
                ", category='" + category + '\'' +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
