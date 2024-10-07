package Liked_Song;

import jakarta.persistence.*;

@Entity
public class LikedSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Songid;

    private String SongName;
    private String SongGenre;

    @Column(unique = true)  // This ensures that the UserID is unique per song
    private int UserID;

    // Constructors
    public LikedSongs(int UserID, String SongName, String SongGenre) {
        this.UserID = UserID;
        this.SongName = SongName;
        this.SongGenre = SongGenre;
    }

    public LikedSongs() {}

    // Getters and Setters
    public int getSongid() {
        return Songid;
    }

    public void setSongid(int Songid) {
        this.Songid = Songid;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String SongName) {
        this.SongName = SongName;
    }

    public String getSongGenre() {
        return SongGenre;
    }

    public void setSongGenre(String SongGenre) {
        this.SongGenre = SongGenre;
    }
}
