package com.example.androidexample;

public class Song {
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
        return album != null ? album.getCover() : null;
    }
    public Long getSongID() {
        return album != null ? album.getSongID() : null;
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

        public Long getSongID() {
            Long SongID = getSongID();
            return SongID;
        }
    }
}