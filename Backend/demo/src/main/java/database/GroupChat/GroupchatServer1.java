package database.GroupChat;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
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

@ServerEndpoint("/chat/1/{username}")
@Component
public class GroupchatServer1 {

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(GroupchatServer1.class);

    private static MessageRepository messageRepository;

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        messageRepository = context.getBean(MessageRepository.class);
    }

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

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);
        logger.info("[onClose] " + username);

        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        broadcast(username + " disconnected");
    }

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
