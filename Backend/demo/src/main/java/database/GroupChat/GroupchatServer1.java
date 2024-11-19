package database.GroupChat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * WebSocket server for group chat functionality.
 * <p>
 * This server handles WebSocket connections for real-time group chat.
 */
@ServerEndpoint("/chat/1/{username}")
@Component
@Tag(name = "Group Chat WebSocket", description = "Handles WebSocket-based group chat interactions.")
public class GroupchatServer1 {

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(GroupchatServer1.class);

    private static MessageRepository messageRepository;

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        messageRepository = context.getBean(MessageRepository.class);
    }

    /**
     * Handles a new WebSocket connection.
     *
     * @param session  The WebSocket session.
     * @param username The username of the user joining the chat.
     * @throws IOException If an error occurs while handling the connection.
     */
    @Operation(summary = "Open WebSocket connection", description = "Handles a new WebSocket connection and registers the user.")
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        logger.info("[onOpen] " + username);

        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        } else {
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);

            // Retrieve and send chat history to the new user
            if (messageRepository != null) {
                List<Message> chatHistory = messageRepository.findAll();
                for (Message message : chatHistory) {
                    session.getBasicRemote().sendText(
                            message.getTimestamp() + " " + message.getSender() + ": " + message.getContent());
                }
            }

            broadcast("User: " + username + " has joined the chat");
        }
    }

    /**
     * Handles an incoming message from a user.
     *
     * @param session        The WebSocket session.
     * @param messageContent The content of the message.
     * @throws IOException If an error occurs while broadcasting the message.
     */
    @Operation(summary = "Handle incoming messages", description = "Broadcasts messages sent by a user to all connected users.")
    @OnMessage
    public void onMessage(Session session, String messageContent) throws IOException {
        String username = sessionUsernameMap.get(session);
        logger.info("[onMessage] " + username + ": " + messageContent);

        // Save the new message to the database
        if (messageRepository != null) {
            Message message = new Message(username, messageContent, LocalDateTime.now());
            messageRepository.save(message);
        }

        // Broadcast the message to all users
        broadcast(username + ": " + messageContent);
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session.
     * @throws IOException If an error occurs during the disconnect process.
     */
    @Operation(summary = "Handle connection closure", description = "Removes the user from the chat when they disconnect.")
    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);

        if (username != null) {
            logger.info("[onClose] " + username);
            sessionUsernameMap.remove(session);
            usernameSessionMap.remove(username);
            broadcast(username + " disconnected");
        } else {
            logger.warn("[onClose] Session username is null, possibly already removed.");
        }
    }

    /**
     * Handles errors during WebSocket communication.
     *
     * @param session   The WebSocket session.
     * @param throwable The error encountered.
     */
    @Operation(summary = "Handle errors", description = "Logs any errors that occur during WebSocket communication.")
    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = sessionUsernameMap.get(session);
        logger.info("[onError] " + username + ": " + throwable.getMessage());
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }
}
