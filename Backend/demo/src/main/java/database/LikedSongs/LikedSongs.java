package database.LikedSongs;

import database.User.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class LikedSongs {

    @Id
    @Column(name = "song_id", nullable = false)
    private Long songID;
    private String songTitle;
    private String genre;

    //collection of users who liked specific song
    @ManyToMany(mappedBy = "likedSongs")
    private Set<User> likedUsers = new HashSet<>();


    public LikedSongs(long songID, String songTitle, String genre) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.genre = genre;
    }

    public LikedSongs() {}


    // Getters and setters
    public Long getSongID() {return songID;}
    public String getSongTitle() {return songTitle;}
    public String getGenre() {return genre;}
    public Set<User> getLikedUsers() {return likedUsers;}

    public void setLikedUsers(Set<User> likedUsers) {this.likedUsers = likedUsers;}
    public void setSongID(long songID) {this.songID = songID;}
    public void setSongTitle(String songTitle) {this.songTitle = songTitle;}
    public void setGenre(String genre) {this.genre = genre;}

}
