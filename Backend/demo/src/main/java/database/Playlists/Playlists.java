package database.Playlists;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Playlists {

    @Id
    private int playlistId;
    private String playlistName;
    private String playlistDescription;


    //each playlist entry functions as an entry for a song that
    //will be added to the user's playlist
    private int songEntryId;


    public Playlists(int playlistId, String playlistName, String playlistDescription, int songEntryId) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistDescription = playlistDescription;
        this.songEntryId = songEntryId;
    }

    public Playlists() {}


    public int getPlaylistId() {return playlistId;}
    public void setPlaylistId(int playlistId) {this.playlistId = playlistId;}
    public String getPlaylistName() {return playlistName;}
    public void setPlaylistName(String playlistName) {this.playlistName = playlistName;}
    public String getPlaylistDescription() {return playlistDescription;}
    public void setPlaylistDescription(String playlistDescription) {this.playlistDescription = playlistDescription;}
    public int getSongEntryId() {return songEntryId;}
    public void setSongEntryId(int songEntryId) {this.songEntryId = songEntryId;}








}
