package database.Notifications;

import database.SimilarGenres.SimilarGenres;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

    @Controller
    public class NotificationController {

        @Operation(summary = "Send a notification")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "successfully sent notification",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = NotificationConfiguration.class)) }),
                @ApiResponse(responseCode = "400", description = "Notification failed to send",
                        content = @Content)})
        @MessageMapping("/send")
        @SendTo("/topic/notifications")
        public String sendNotification(String message) {
        return message;
        }
    }
