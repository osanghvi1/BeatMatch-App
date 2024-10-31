package database.Friends;

import jakarta.persistence.*;

@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated primary key

    private String userName; // Unique username of the user
    private String userNameFriends; // Friend's username

    public Friends(String userName, String userNameFriends) {
        this.userName = userName;
        this.userNameFriends = userNameFriends;
    }

    public Friends() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameFriends() {
        return userNameFriends;
    }

    public void setUserNameFriends(String userNameFriends) {
        this.userNameFriends = userNameFriends;
    }
}
