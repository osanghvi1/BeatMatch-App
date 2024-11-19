package database.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

/**
 * Repository interface for managing `Friends` entities.
 * <p>
 * Provides methods for CRUD operations and custom queries to handle friendships.
 */
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    /**
     * Finds all friendships associated with a specific user ID.
     *
     * @param userID The ID of the user.
     * @return A list of `Friends` entities related to the user.
     */
    @Operation(summary = "Find friendships by user ID", description = "Retrieve all friendships for a specific user ID.")
    List<Friends> findByUserID(int userID);

    /**
     * Finds a specific friendship between two users.
     *
     * @param userID        The ID of the main user.
     * @param userIDFriends The ID of the friend.
     * @return A `Friends` entity representing the friendship, or `null` if not found.
     */
    @Operation(summary = "Find a friendship by user IDs", description = "Retrieve a specific friendship between two users by their IDs.")
    Friends findByUserIDAndUserIDFriends(int userID, int userIDFriends);

    /**
     * Deletes a friendship between two users.
     * <p>
     * This method is transactional to ensure atomicity during the deletion process.
     *
     * @param userID        The ID of the main user.
     * @param userIDFriends The ID of the friend.
     */
    @Operation(summary = "Delete a friendship by user IDs", description = "Delete a specific friendship between two users by their IDs.")
    @Transactional
    void deleteByUserIDAndUserIDFriends(int userID, int userIDFriends);
}
