package database.Friends;

import database.User.User;
import database.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for managing friendships between users.
 */
@RestController
@Tag(name = "Friends API", description = "Endpoints for managing friendships between users.")
public class FriendsController {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    /**
     * Get all friendships.
     *
     * @return A list of all friendships.
     */
    @Operation(summary = "Get all friendships", description = "Retrieve a list of all friendships.")
    @GetMapping(path = "/friends")
    public List<Friends> getAllFriendships() {
        return friendsRepository.findAll();
    }

    /**
     * Get friendships by user ID.
     *
     * @param userID The ID of the user.
     * @return A list of friendships associated with the user ID.
     */
    @Operation(summary = "Get friendships by user ID", description = "Retrieve friendships associated with a specific user ID.")
    @GetMapping(path = "/friends/{userID}")
    public List<Friends> getFriendsByUserID(@PathVariable int userID) {
        return friendsRepository.findByUserID(userID);
    }

    /**
     * Create a new friendship.
     *
     * @param friendship The friendship object to create.
     * @return A success or failure message.
     */
    @Operation(summary = "Create a new friendship", description = "Create a new friendship between two users.")
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

    /**
     * Delete a friendship by user IDs.
     *
     * @param userID        The ID of the main user.
     * @param userIDFriends The ID of the friend.
     * @return A success or failure message.
     */
    @Operation(summary = "Delete a friendship", description = "Delete a friendship by user IDs.")
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
