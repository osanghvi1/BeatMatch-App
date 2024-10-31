package database.Friends;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for each friendship

    private int userID;           // ID of the main user
    private int userIDFriends;     // ID of the friend

    public Friends() {}

    public Friends(int userID, int userIDFriends) {
        this.userID = userID;
        this.userIDFriends = userIDFriends;
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
}
