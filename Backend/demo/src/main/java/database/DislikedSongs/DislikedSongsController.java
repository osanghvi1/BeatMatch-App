package database.DislikedSongs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DislikedSongsController {

    @Autowired
    private DislikedSongsRepository dislikedSongsRepository;





}
