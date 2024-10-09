package database.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    @Transactional
    void deleteById(int id);

    User findByEmailAndPassword(String email, String password);
}
