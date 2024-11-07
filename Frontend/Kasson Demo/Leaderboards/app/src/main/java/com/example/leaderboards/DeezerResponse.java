package com.example.leaderboards;

import java.util.List;

public class DeezerResponse {
    private List<Track> data;

    public List<Track> getData() {
        return data;
    }

    public static class Track {
        private String title;
        private String artist;
        private String album;
        private String cover;
        private Long songID;

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public Long getSongID() {
            return songID;
        }

        public void setSongID(Long songID) {
            this.songID = songID;
        }
    }
}
