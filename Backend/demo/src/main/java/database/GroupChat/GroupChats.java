package database.GroupChat;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GroupChats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    private String groupImage;

    @ManyToMany
    @JoinTable(
            name = "groupchat_users",
            joinColumns = @JoinColumn(name = "groupchat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<database.User.User> users = new HashSet<>();

    public GroupChats() {}

    public GroupChats(String groupName, String groupImage) {
        this.groupName = groupName;
        this.groupImage = groupImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public Set<database.User.User> getUsers() {
        return users;
    }

    public void setUsers(Set<database.User.User> users) {
        this.users = users;
    }
}
