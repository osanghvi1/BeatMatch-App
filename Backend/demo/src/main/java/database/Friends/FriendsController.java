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

    // Get friendship by userName
    @GetMapping(path = "/friends/{userName}")
    public List<Friends> getFriendsByUserName(@PathVariable String userName) {
        return friendsRepository.findByUserName(userName);
    }

    // Create a new friendship
    @PostMapping(path = "/friends/create")
    public String createFriendship(@RequestBody Friends friendship) {
        if (friendship == null || friendship.getUserName() == null || friendship.getUserNameFriends() == null) {
            return failure;
        }
        friendsRepository.save(friendship);
        return success;
    }

    // Delete a friendship by userName and userNameFriends
    @DeleteMapping(path = "/friends/delete/{userName}/{userNameFriends}")
    public String deleteFriendship(@PathVariable String userName, @PathVariable String userNameFriends) {
        Friends friendship = friendsRepository.findByUserNameAndUserNameFriends(userName, userNameFriends);
        if (friendship != null) {
            friendsRepository.delete(friendship);
            return success;
        } else {
            return failure;
        }
    }
}
