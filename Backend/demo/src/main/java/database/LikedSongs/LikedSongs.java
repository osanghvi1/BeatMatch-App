package database.LikedSongs;

import jakarta.persistence.*;

@Entity
public class LikedSongs {

    @Id
    @Column(name = "song_id")
    private Long songID;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "song_title", nullable = true)
    private String songTitle;

    // Getters and setters
    public Long getSongID() {
        return songID;
    }

    public void setSongID(Long songID) {
        this.songID = songID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
