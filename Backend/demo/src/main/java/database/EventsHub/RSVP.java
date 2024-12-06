package database.EventsHub;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import database.User.User;

@Entity
@Table(name = "event_rsvp")
public class RSVP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsvp_id")
    private Long rsvpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private EventsHub event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rsvp_status", nullable = false)
    private String rsvpStatus;

    // Default constructor
    public RSVP() {}

    // Parameterized constructor
    public RSVP(EventsHub event, User user, String rsvpStatus) {
        this.event = event;
        this.user = user;
        this.rsvpStatus = rsvpStatus;
    }

    // Getters and Setters
    public Long getRsvpId() { return rsvpId; }
    public void setRsvpId(Long rsvpId) { this.rsvpId = rsvpId; }

    public EventsHub getEvent() { return event; }
    public void setEvent(EventsHub event) { this.event = event; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getRsvpStatus() { return rsvpStatus; }
    public void setRsvpStatus(String rsvpStatus) { this.rsvpStatus = rsvpStatus; }

    @Override
    public String toString() {
        return "RSVP{" +
                "rsvpId=" + rsvpId +
                ", event=" + (event != null ? event.getEventId() : null) +
                ", user=" + (user != null ? user.getUserID() : null) +
                ", rsvpStatus='" + rsvpStatus + '\'' +
                '}';
    }
}
