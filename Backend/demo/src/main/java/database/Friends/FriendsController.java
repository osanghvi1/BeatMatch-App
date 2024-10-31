package database.Friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendsController {

    @Autowired
    private FriendsRepository friendsRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Get all friendships
    @GetMapping(path = "/friends")
    public List<Friends> getAllFriendships() {
        return friendsRepository.findAll();
    }

    // Get friendships by userID
    @GetMapping(path = "/friends/{userID}")
    public List<Friends> getFriendsByUserID(@PathVariable int userID) {
        return friendsRepository.findByUserID(userID);
    }

    // Create a new friendship
    @PostMapping(path = "/friends/create")
    public String createFriendship(@RequestBody Friends friendship) {
        if (friendship == null || friendship.getUserID() == 0 || friendship.getUserIDFriends() == 0) {
            return failure;
        }
        friendsRepository.save(friendship);
        return success;
    }

    // Delete a friendship by userID and userIDFriends
    @DeleteMapping(path = "/friends/delete/{userID}/{userIDFriends}")
    public String deleteFriendship(@PathVariable int userID, @PathVariable int userIDFriends) {
        Friends friendship = friendsRepository.findByUserIDAndUserIDFriends(userID, userIDFriends);
        if (friendship != null) {
            friendsRepository.delete(friendship);
            return success;
        } else {
            return failure;
        }
    }
}
