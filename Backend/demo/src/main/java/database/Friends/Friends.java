package database.Friends;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Om Sanghvi Made this change
@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for each friendship

    private int userID;           // ID of the main user
    private int userIDFriends;     // ID of the friend
    private String userName;       // Name of the main user
    private String userNameFriend; // Name of the friend

    public Friends() {}

    public Friends(int userID, int userIDFriends, String userName, String userNameFriend) {
        this.userID = userID;
        this.userIDFriends = userIDFriends;
        this.userName = userName;
        this.userNameFriend = userNameFriend;
    }

    // Getters and setters
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
