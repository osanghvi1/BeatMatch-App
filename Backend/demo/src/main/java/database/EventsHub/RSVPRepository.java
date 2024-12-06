package database.EventsHub;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RSVPRepository extends CrudRepository<RSVP, Long> {

    // Custom query to find all RSVPs by event ID
    @Query("SELECT r FROM RSVP r WHERE r.event.eventId = :eventId")
    List<RSVP> findRSVPsByEventId(@Param("eventId") Long eventId);
}
