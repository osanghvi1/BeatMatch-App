package com.example.androidexample;

import java.util.List;

public class DeezerResponse {
    private List<Track> data;

    public List<Track> getData() {
        return data;
    }

    public static class Track {
        private String title;
        private Artist artist;
        private Album album;

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist != null ? artist.getName() : "Unknown Artist";
        }

        public String getAlbum() {
            return album != null ? album.getTitle() : "Unknown Album";
        }

        public String getCover() {
            return album != null ? album.getCover() : "No cover available";
        }

        public static class Artist {
            private String name;

            public String getName() {
                return name;
            }
        }

        public static class Album {
            private String title;
            private String cover;

            public String getTitle() {
                return title;
            }

            public String getCover() {
                return cover;
            }
        }
    }
}