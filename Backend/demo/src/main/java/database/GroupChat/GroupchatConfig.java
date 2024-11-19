package database.GroupChat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Configuration class for setting up WebSocket support in the application.
 * <p>
 * This class registers WebSocket endpoints so that `ws://` requests are handled correctly.
 */
@Configuration
@Tag(name = "Group Chat Configuration", description = "Configuration for WebSocket endpoints for group chat.")
public class GroupchatConfig {

    /**
     * Registers a `ServerEndpointExporter` bean to enable WebSocket endpoints.
     *
     * @return A `ServerEndpointExporter` instance for WebSocket endpoint handling.
     */
    @Operation(summary = "Register WebSocket endpoints", description = "Registers the WebSocket endpoint exporter to enable WebSocket communication.")
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
