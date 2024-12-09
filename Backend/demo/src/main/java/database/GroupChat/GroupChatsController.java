package database.GroupChat;

import database.User.User;
import database.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/groupchats")
@Tag(name = "Group Chats Controller", description = "Manage group chats.")
public class GroupChatsController {

    @Autowired
    private GroupChatsRepository groupChatsRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new group chat.
     *
     * @param groupChat The group chat to create.
     * @return The created group chat.
     */
    @PostMapping
    @Operation(summary = "Create Group Chat", description = "Create a new group chat.")
    public ResponseEntity<GroupChats> createGroupChat(@RequestBody GroupChats groupChat) {
        GroupChats savedGroupChat = groupChatsRepository.save(groupChat);
        return ResponseEntity.ok(savedGroupChat);
    }
    /**
     * Update an existing group chat.
     *
     * @param groupChat The group chat with updated details.
     * @return The updated group chat.
     */
    @PutMapping
    @Operation(summary = "Update Group Chat", description = "Update an existing group chat.")
    public ResponseEntity<GroupChats> updateGroupChat(@RequestBody GroupChats groupChat) {
        if (groupChat.getId() == null || !groupChatsRepository.existsById(groupChat.getId())) {
            return ResponseEntity.notFound().build(); // Ensure the group chat exists
        }
        GroupChats updatedGroupChat = groupChatsRepository.save(groupChat);
        return ResponseEntity.ok(updatedGroupChat);
    }


    /**
     * Add a user to a group chat.
     *
     * @param groupId The ID of the group chat.
     * @param userId  The ID of the user to add.
     * @return The updated group chat.
     */
    @PostMapping("/{groupId}/addUser/{userId}")
    @Operation(summary = "Add User to Group Chat", description = "Add a user to a group chat.")
    public ResponseEntity<GroupChats> addUserToGroupChat(@PathVariable Long groupId, @PathVariable int userId) {
        GroupChats groupChat = groupChatsRepository.findById(groupId).orElse(null);
        if (groupChat == null) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findById(userId); // Simplified
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (groupChat.getUsers().contains(user)) {
            return ResponseEntity.badRequest().body(null); // User already in the group
        }

        groupChat.getUsers().add(user);
        groupChatsRepository.save(groupChat);
        return ResponseEntity.ok(groupChat);
    }

    /**
     * Get all group chats.
     *
     * @return List of all group chats.
     */
    @GetMapping
    @Operation(summary = "Get All Group Chats", description = "Retrieve all group chats.")
    public ResponseEntity<List<GroupChats>> getAllGroupChats() {
        List<GroupChats> groupChats = groupChatsRepository.findAll();
        return ResponseEntity.ok(groupChats);
    }

    /**
     * Get a specific group chat by ID.
     *
     * @param groupId The ID of the group chat.
     * @return The group chat details.
     */
    @GetMapping("/{groupId}")
    @Operation(summary = "Get Group Chat by ID", description = "Retrieve a specific group chat by ID.")
    public ResponseEntity<GroupChats> getGroupChatById(@PathVariable Long groupId) {
        GroupChats groupChat = groupChatsRepository.findById(groupId).orElse(null);
        if (groupChat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groupChat);
    }

    /**
     * Remove a user from a group chat.
     *
     * @param groupId The ID of the group chat.
     * @param userId  The ID of the user to remove.
     * @return The updated group chat.
     */
    @DeleteMapping("/{groupId}/removeUser/{userId}")
    @Operation(summary = "Remove User from Group Chat", description = "Remove a user from a group chat.")
    public ResponseEntity<GroupChats> removeUserFromGroupChat(@PathVariable Long groupId, @PathVariable int userId) {
        GroupChats groupChat = groupChatsRepository.findById(groupId).orElse(null);
        if (groupChat == null) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findById(userId); // Simplified
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        groupChat.getUsers().remove(user);
        groupChatsRepository.save(groupChat);
        return ResponseEntity.ok(groupChat);
    }
}
