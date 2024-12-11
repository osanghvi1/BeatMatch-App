package database.Leaderboard;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a leaderboard entry.
 * <p>
 * Stores information about songs ranked on the leaderboard, including their ID, rank, likes, and category.
 */
@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaderboard_id")
    private Long id;

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

    @Column(name = "song_name")
    private String songName;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album_cover")
    private String albumCover;

    // Default constructor
    public Leaderboard() {}

    // Parameterized constructor
    public Leaderboard(Long songID, int rank, int likeCount, String category, LocalDateTime updatedTime,
                       String songName, String artist, String albumCover) {
        this.songID = songID;
        this.rank = rank;
        this.likeCount = likeCount;
        this.category = category;
        this.updatedTime = updatedTime;
        this.songName = songName;
        this.artist = artist;
        this.albumCover = albumCover;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    @Override
    public String toString() {
        return "Leaderboard{" +
                "id=" + id +
                ", songID=" + songID +
                ", rank=" + rank +
                ", likeCount=" + likeCount +
                ", category='" + category + '\'' +
                ", updatedTime=" + updatedTime +
                ", songName='" + songName + '\'' +
                ", artist='" + artist + '\'' +
                ", albumCover='" + albumCover + '\'' +
                '}';
    }
}
