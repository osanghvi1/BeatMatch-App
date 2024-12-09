package database.EventsHub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsHubRepository extends JpaRepository<EventsHub, Long> {
    List<EventsHub> findAll(); // Return as List instead of Iterable
}
