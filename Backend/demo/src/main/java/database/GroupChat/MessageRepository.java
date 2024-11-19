package database.GroupChat;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing `Message` entities.
 * <p>
 * Provides methods for performing database operations on chat messages.
 */
@Repository
@Tag(name = "Message Repository", description = "Handles database operations for group chat messages.")
public interface MessageRepository extends JpaRepository<Message, Long> {
}
