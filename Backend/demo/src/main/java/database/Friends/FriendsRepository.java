package database.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {

    List<Friends> findByUserID(int userID);

    Friends findByUserIDAndUserIDFriends(int userID, int userIDFriends);

    @Transactional
    void deleteByUserIDAndUserIDFriends(int userID, int userIDFriends);
}
