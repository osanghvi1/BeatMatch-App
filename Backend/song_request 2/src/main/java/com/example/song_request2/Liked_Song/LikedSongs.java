package com.example.song_request2.Liked_Song;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LikedSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key for the liked song entry

    private long songId; // Deezer Track ID
    private String songName;
    private String songGenre;
    private int userID; // Assuming userID is an integer for this example

    public LikedSongs() {}

    public LikedSongs(long songId, String songName, String songGenre, int userID) {
        this.songId = songId;
        this.songName = songName;
        this.songGenre = songGenre;
        this.userID = userID;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
