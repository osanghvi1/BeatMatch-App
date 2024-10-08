package com.example.androidexample;

public class Track {
    private String id;
    private String title;
    private Artist artist;
    private Genre genre; // Ensure this is the right type for the genre

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Genre getGenre() {
        return genre;
    }
}