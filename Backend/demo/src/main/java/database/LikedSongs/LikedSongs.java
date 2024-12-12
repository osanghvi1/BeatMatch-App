package database.LikedSongs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import database.User.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a liked song entity.
 * <p>
 * Stores information about songs liked by users, including the song ID, title, genre, and users who liked it.
 */
@Entity
@Schema(description = "Entity representing a liked song and its associated users.")
public class LikedSongs {

    @Id
    @Column(name = "song_id", nullable = false)
    @Schema(description = "Unique identifier for the song.", example = "101")
    private Long songID;

    @Schema(description = "Title of the song.", example = "Shape of You")
    private String songTitle;

    @Schema(description = "Genre of the song.", example = "Pop")
    private String genre;

    @ManyToMany(mappedBy = "likedSongs")
    @Schema(description = "Collection of users who liked this song.")
    @JsonIgnore
    private Set<User> likedUsers = new HashSet<>();

    // Constructors
    public LikedSongs(long songID, String songTitle, String genre) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.genre = genre;
    }

    public LikedSongs() {}

    // Getters and setters

    @Schema(description = "Gets the unique ID of the song.")
    public Long getSongID() {
        return songID;
    }

    public void setSongID(long songID) {
        this.songID = songID;
    }

    @Schema(description = "Gets the title of the song.")
    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    @Schema(description = "Gets the genre of the song.")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Schema(description = "Gets the collection of users who liked this song.")
    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }
}
