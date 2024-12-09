package database.Playlists;

import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaylistController {

    @Autowired
    private UserRepository userRepository;

    //getter for getting all song entries by playlist id
    //Will essentially return the songs in a playlist



    //create a song entry



    //don't think there should be an option to update a
    //song's entry in a playlst. Can't really see that
    //functionality being useful.



    //delete a song entry


}
