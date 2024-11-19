package database.Leaderboard;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a leaderboard entry.
 * <p>
 * Stores information about songs ranked on the leaderboard, including their ID, rank, likes, and category.
 */
@Entity
@Table(name = "leaderboard")
@Schema(description = "Entity representing a leaderboard entry for songs.")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaderboard_id")
    @Schema(description = "Unique identifier for the leaderboard entry.", example = "1")
    private Long leaderboardID;

    @Column(name = "song_id", nullable = false)
    @Schema(description = "Unique identifier for the song.", example = "101")
    private Long songID;

    @Column(name = "rank", nullable = false)
    @Schema(description = "Rank of the song on the leaderboard.", example = "1")
    private int rank;

    @Column(name = "like_count", nullable = false)
    @Schema(description = "Total number of likes the song has received.", example = "500")
    private int likeCount;

    @Column(name = "category")
    @Schema(description = "Category of the leaderboard entry.", example = "Top Hits")
    private String category;

    @Column(name = "updated_time")
    @Schema(description = "Timestamp when the leaderboard entry was last updated.", example = "2024-11-18T15:30:00")
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

    public void setLeaderboardID(Long leaderboardID) { this.leaderboardID = leaderboardID; }

    public Long getSongID() { return songID; }

    public void setSongID(Long songID) { this.songID = songID; }

    public int getRank() { return rank; }

    public void setRank(int rank) { this.rank = rank; }

    public int getLikeCount() { return likeCount; }

    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getUpdatedTime() { return updatedTime; }

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
