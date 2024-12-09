package database.PlaylistEntry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistEntryRepository extends JpaRepository<PlaylistEntry, Integer> {

    //to check if an entry in a specific playlist has already been added
    PlaylistEntry findEntryByPidAndSid(int pid, int sid);

    //get all the songs in a playlist
    List<PlaylistEntry> getPlaylist(int pid);



}
