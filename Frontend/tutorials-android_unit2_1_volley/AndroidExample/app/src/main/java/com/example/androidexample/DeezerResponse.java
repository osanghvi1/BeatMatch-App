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
        private String id; // This is the song ID in Deezer's API

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist.getName();
        }

        public String getAlbum() {
            return album.getTitle();
        }

        public String getCover() {
            return album.getCover();
        }

        public String getSongID() {
            return id;
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