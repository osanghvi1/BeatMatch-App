package database.PlaylistEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaylistEntryController {

    @Autowired
    private PlaylistEntryRepository playlistEntryRepository;

    //getter for getting all song entries by playlist id
    //Will essentially return the songs in a playlist
    @GetMapping(path = "/playlistEntry/{pid}")
    public List<PlaylistEntry> getPlaylistEntry(@PathVariable int pid) {
        return playlistEntryRepository.getPlaylistEntriesByPlaylistId(pid);
    }

    //create a song entry
    @PostMapping(path = "/playlistEntry/addSong")
    public String addSong(@RequestBody PlaylistEntry entry){
        //Get entry to add info
        int songId = entry.getSongEntryId();
        int playlistId = entry.getPlaylistId();

        //Get info from potential entry already in database
        PlaylistEntry dbEntry = playlistEntryRepository.findByPlaylistIdAndSongEntryId(playlistId, songId);

        //if they don't send over an entry correctly
        if(entry == null){
            return "Invalid Playlist Entry";
        }
        else if(dbEntry != null){
            return "Entry already Exists in database";
        }
        else{
            playlistEntryRepository.save(entry);
            return "Added song to playlist w/ Sid: " + entry.getSongEntryId() + " and playlistId: " + entry.getPlaylistId();
        }
    }


    //delete a song entry
    @DeleteMapping(path = "/playlistEntry/delete/{pid}/{sid}")
    public String deleteSongEntry(@PathVariable int pid, @PathVariable int sid) {
        PlaylistEntry entry = playlistEntryRepository.findByPlaylistIdAndSongEntryId(pid, sid);
        if (entry == null) {
            return "Playlist Entry does not exist";
        }
        else{
            playlistEntryRepository.delete(entry);
            if(playlistEntryRepository.findByPlaylistIdAndSongEntryId(entry.getPlaylistId(), entry.getSongEntryId()) == null){
                return "Deleted playlist entry";
            }
            return "Delete Failure";
        }
    }

    @DeleteMapping(path = "/playlistEntry/deletePlaylist/{pid}")
    public String deletePlaylist(@PathVariable int pid) {
        List<PlaylistEntry> playlist = playlistEntryRepository.getPlaylistEntriesByPlaylistId(pid);
        playlistEntryRepository.deleteAllById(pid);
    }

}
