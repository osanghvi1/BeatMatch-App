package database.DislikedSongs;

import database.User.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.Set;

@Entity
public class DislikedSongs {

    @Id
    private int songID;
    private String songTitle;
    private String genre;

    //collection of users who liked specific song
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
            @JoinTable(name = "user_dislikeSong_mapping", joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> dislikedUsers = null;


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
