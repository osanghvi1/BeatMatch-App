package database.EventsHub;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsHubController {

    private final EventsHubRepository eventsHubRepository;
    private final RSVPRepository rsvpRepository;

    public EventsHubController(EventsHubRepository eventsHubRepository, RSVPRepository rsvpRepository) {
        this.eventsHubRepository = eventsHubRepository;
        this.rsvpRepository = rsvpRepository;
    }

    @PostMapping
    public ResponseEntity<EventsHub> createEvent(@RequestBody EventsHub event) {
        EventsHub savedEvent = eventsHubRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @GetMapping
    public ResponseEntity<List<EventsHub>> getAllEvents() {
        List<EventsHub> events = (List<EventsHub>) eventsHubRepository.findAll(); // Cast to List
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventsHub> getEventById(@PathVariable Long id) {
        return eventsHubRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventsHub> updateEvent(@PathVariable Long id, @RequestBody EventsHub updatedEvent) {
        return eventsHubRepository.findById(id).map(event -> {
            event.setEventName(updatedEvent.getEventName());
            event.setEventDate(updatedEvent.getEventDate());
            event.setEventLocation(updatedEvent.getEventLocation());
            event.setEventType(updatedEvent.getEventType());
            EventsHub savedEvent = eventsHubRepository.save(event);
            return ResponseEntity.ok(savedEvent);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        return eventsHubRepository.findById(id).map(event -> {
            eventsHubRepository.delete(event);
            return ResponseEntity.noContent().build(); // Return a ResponseEntity<Void>
        }).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/{eventId}/rsvp")
    public ResponseEntity<RSVP> addRSVP(@PathVariable Long eventId, @RequestBody RSVP rsvp) {
        return eventsHubRepository.findById(eventId).map(event -> {
            rsvp.setEvent(event);
            RSVP savedRSVP = rsvpRepository.save(rsvp);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRSVP);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{eventId}/rsvps")
    public ResponseEntity<List<RSVP>> getRSVPsByEvent(@PathVariable Long eventId) {
        List<RSVP> rsvps = rsvpRepository.findRSVPsByEventId(eventId);
        return ResponseEntity.ok(rsvps);
    }
}
