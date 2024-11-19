package database.Friends;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a friendship between two users.
 */
@Entity
@Schema(description = "Represents a friendship relationship between two users.")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the friendship.", example = "1")
    private Long id;

    @Schema(description = "The main user's ID.", example = "1001")
    private int userID;

    @Schema(description = "The friend's ID.", example = "1002")
    private int userIDFriends;

    @Schema(description = "The main user's name.", example = "John Doe")
    private String userName;

    @Schema(description = "The friend's name.", example = "Jane Smith")
    private String userNameFriend;

    public Friends() {}

    public Friends(int userID, int userIDFriends, String userName, String userNameFriend) {
        this.userID = userID;
        this.userIDFriends = userIDFriends;
        this.userName = userName;
        this.userNameFriend = userNameFriend;
    }

    public Long getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserIDFriends() {
        return userIDFriends;
    }

    public void setUserIDFriends(int userIDFriends) {
        this.userIDFriends = userIDFriends;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameFriend() {
        return userNameFriend;
    }

    public void setUserNameFriend(String userNameFriend) {
        this.userNameFriend = userNameFriend;
    }
}
