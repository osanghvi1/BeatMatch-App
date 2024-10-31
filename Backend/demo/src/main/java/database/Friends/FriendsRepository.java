package database.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {

    List<Friends> findByUserName(String userName); // Return a list of friends

    @Transactional
    void deleteByUserName(String userName);

    Friends findByUserNameAndUserNameFriends(String userName, String userNameFriends);
}
