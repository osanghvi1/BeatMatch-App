package database.DislikedSongs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import database.User.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.HashSet;
import java.util.Set;

@Entity
public class DislikedSongs {

    @Id
    private int songID;
    private String songTitle;
    private String genre;

    //collection of users who liked specific song
    @ManyToMany(mappedBy = "dislikedSongs")
    @JsonIgnore
    private Set<User> dislikedUsers = new HashSet<>();


    public DislikedSongs(int songID, String songTitle, String genre) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.genre = genre;
    }

    public DislikedSongs() {}

    public int getSongID() {return songID;}
    public String getSongTitle() {return songTitle;}
    public String getGenre() {return genre;}

    public void setSongID(int songID) {this.songID = songID;}
    public void setSongTitle(String songTitle) {this.songTitle = songTitle;}
    public void setGenre(String genre) {this.genre = genre;}

    public Set<User> getDislikedUsers() {return dislikedUsers;}
    public void setDislikedUsers(Set<User> dislikedUsers) {this.dislikedUsers = dislikedUsers;}

}
