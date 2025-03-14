package com.example.openingactivity;

public class PlaylistEntry {
    private int playlistId;
    private String playlistName;
    private String playlistDescription;
    private int songEntryId;

    // Constructors
    public PlaylistEntry() {}

    public PlaylistEntry(int playlistId, String playlistName, String playlistDescription, int songEntryId) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistDescription = playlistDescription;
        this.songEntryId = songEntryId;
    }

    // Getters and Setters
    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    public int getSongEntryId() {
        return songEntryId;
    }

    public void setSongEntryId(int songEntryId) {
        this.songEntryId = songEntryId;
    }
}

