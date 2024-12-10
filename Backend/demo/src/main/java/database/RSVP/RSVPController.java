package database.RSVP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rsvps")
public class RSVPController {

    @Autowired
    private RSVPRepository rsvpRepository;

    // Get all RSVPs
    @GetMapping
    public ResponseEntity<List<RSVP>> getAllRSVPs() {
        List<RSVP> rsvps = rsvpRepository.findAll();
        return ResponseEntity.ok(rsvps);
    }

    // Get an RSVP by ID
    @GetMapping("/{id}")
    public ResponseEntity<RSVP> getRSVPById(@PathVariable Long id) {
        return rsvpRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new RSVP
    @PostMapping
    public ResponseEntity<RSVP> createRSVP(@RequestBody RSVP rsvp) {
        RSVP savedRSVP = rsvpRepository.save(rsvp);
        return ResponseEntity.ok(savedRSVP);
    }

    // Update an RSVP
    @PutMapping("/{id}")
    public ResponseEntity<RSVP> updateRSVP(@PathVariable Long id, @RequestBody RSVP updatedRSVP) {
        return rsvpRepository.findById(id)
                .map(existingRSVP -> {
                    existingRSVP.setUserName(updatedRSVP.getUserName());
                    existingRSVP.setEmail(updatedRSVP.getEmail());
                    existingRSVP.setResponse(updatedRSVP.getResponse());
                    RSVP savedRSVP = rsvpRepository.save(existingRSVP);
                    return ResponseEntity.ok(savedRSVP);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an RSVP
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRSVP(@PathVariable Long id) {
        if (rsvpRepository.existsById(id)) {
            rsvpRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
