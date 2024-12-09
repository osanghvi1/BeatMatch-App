package database.EventsHub;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RSVP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    private String response;

    private LocalDateTime rsvpTime;

    public RSVP() {
        this.rsvpTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getRsvpTime() {
        return rsvpTime;
    }

    public void setRsvpTime(LocalDateTime rsvpTime) {
        this.rsvpTime = rsvpTime;
    }
}
