package database.GroupChat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Represents a chat message in the group chat system.
 */
@Entity
@Schema(description = "Entity representing a chat message in the group chat.")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the message.", example = "1")
    private Long id;

    @Schema(description = "Username of the sender.", example = "john_doe")
    private String sender;

    @Schema(description = "Content of the message.", example = "Hello, world!")
    private String content;

    @Schema(description = "Timestamp of when the message was sent.", example = "2024-11-18T20:15:30")
    private LocalDateTime timestamp;

    public Message() {}

    /**
     * Constructor for creating a new `Message` instance.
     *
     * @param sender    Username of the message sender.
     * @param content   Content of the message.
     * @param timestamp Timestamp of when the message was sent.
     */
    public Message(String sender, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and setters

    @Schema(description = "Gets the unique ID of the message.")
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Schema(description = "Gets the username of the sender.")
    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    @Schema(description = "Gets the content of the message.")
    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    @Schema(description = "Gets the timestamp of when the message was sent.")
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
