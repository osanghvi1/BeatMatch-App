package database.PlaylistEntry;

import database.User.UserRepository;
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
        return playlistEntryRepository.getPlaylist(pid);
    }

    //create a song entry
    @PostMapping(path = "/playlistEntry/addSong")
    String addSong(@RequestBody PlaylistEntry entry){
        //Get entry to add info
        int songId = entry.getSongEntryId();
        int playlistId = entry.getPlaylistId();

        //Get info from potential entry already in database
        int dbSongId = playlistEntryRepository.findEntryByPidAndSid(playlistId, songId).getSongEntryId();
        int dbPlaylistId = playlistEntryRepository.findEntryByPidAndSid(playlistId, songId).getPlaylistId();

        //if they don't send over an entry correctly
        if(entry == null){
            return "Invalid Playlist Entry";
        }
        else if(songId == dbSongId && playlistId == dbPlaylistId){
            //replace with some exception later
            //about duplicate stuff
            return "Entry already exists in playlist";
        }
        else{
            playlistEntryRepository.save(entry);
            return "Added song to playlist w/ Sid: " + entry.getSongEntryId() + " and playlistId: " + entry.getPlaylistId();
        }
    }


    //delete a song entry
    @DeleteMapping(path = "/playlistEntry/delete/{pid}/{sid}")
    String deleteSongEntry(@PathVariable int pid, @PathVariable int sid) {
        PlaylistEntry entry = playlistEntryRepository.findEntryByPidAndSid(pid, sid);
        if (entry == null) {
            return "Playlist Entry does not exist";
        }
        else{
            playlistEntryRepository.delete(entry);
            if(playlistEntryRepository.findEntryByPidAndSid(entry.getPlaylistId(), entry.getSongEntryId()) == null){
                return "Deleted playlist entry";
            }
            return "Delete Failure";
        }
    }


}
