package com.example.song_request2.Liked_Song;

import jakarta.persistence.*;

@Entity
public class LikedSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int songId; // Use camelCase naming

    private String songName;
    private String songGenre;

    @Column(unique = true)  // This ensures that the userID is unique per song
    private int userID;

    // Constructors
    public LikedSongs(int userID, String songName, String songGenre) {
        this.userID = userID;
        this.songName = songName;
        this.songGenre = songGenre;
    }

    public LikedSongs() {}

    // Getters and Setters
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
}
