package database.Friends;

import database.User.User;
import database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FriendsController {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository; // UserRepository for user validation

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

        // Check if the user is trying to add themselves as a friend
        if (friendship.getUserID() == friendship.getUserIDFriends()) {
            return "{\"message\":\"failure: cannot add yourself as a friend\"}";
        }

        // Validate that both userID and userIDFriends exist in the user database
        User user = userRepository.findById(friendship.getUserID());
        User friend = userRepository.findById(friendship.getUserIDFriends());

        if (user == null || friend == null) {
            return "{\"message\":\"failure: one or both user IDs are invalid\"}";
        }

        // Set user_name and user_name_friend before saving
        friendship.setUserName(user.getUserName());
        friendship.setUserNameFriend(friend.getUserName());

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
