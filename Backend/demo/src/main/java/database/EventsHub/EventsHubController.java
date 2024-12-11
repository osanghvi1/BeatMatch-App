package database.EventsHub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsHubController {

    @Autowired
    private EventsHubRepository eventRepository;

    /**
     * Create a new event.
     * @param event The event data.
     * @param result Binding result for validation.
     * @return Created event or validation errors.
     */
    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventsHub event, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        EventsHub savedEvent = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    /**
     * Get all events.
     * @return List of all events.
     */
    @GetMapping
    public ResponseEntity<List<EventsHub>> getAllEvents() {
        List<EventsHub> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }

    /**
     * Get a single event by ID.
     * @param id The event ID.
     * @return Event if found, or 404 if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventsHub> getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    /**
     * Update an existing event.
     * @param id The event ID to update.
     * @param updatedEvent The updated event data.
     * @param result Binding result for validation.
     * @return Updated event or validation errors.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @Valid @RequestBody EventsHub updatedEvent, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return eventRepository.findById(id)
                .map(event -> {
                    event.setEventName(updatedEvent.getEventName());
                    event.setEventHost(updatedEvent.getEventHost());
                    event.setEventDate(updatedEvent.getEventDate());
                    event.setEventLocation(updatedEvent.getEventLocation());
                    event.setEventThumbnail(updatedEvent.getEventThumbnail());
                    event.setEventCost(updatedEvent.getEventCost());
                    EventsHub savedEvent = eventRepository.save(event);
                    return ResponseEntity.ok(savedEvent);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    /**
     * Delete an event by ID.
     * @param id The event ID to delete.
     * @return 204 No Content if deleted, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
