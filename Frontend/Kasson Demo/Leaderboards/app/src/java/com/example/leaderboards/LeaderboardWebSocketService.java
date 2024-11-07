//package com.example.leaderboards;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import org.java-websocket.client.WebSocketClient;
//import org.java-websocket.handshake.ServerHandshake;
//import org.json.JSONObject;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public class LeaderboardWebSocketService {
//
//    private static final String WEBSOCKET_URL = "ws://your-server-ip:8080/leaderboard-websocket";
//    private WebSocketClient webSocketClient;
//    private LinearLayout leaderboardLayout;
//
//    // Sample leaderboard data that you might send to the backend
//    private long leaderboardID = 1;  // This should come from your backend or be generated dynamically
//    private String category = "General";  // Category of the leaderboard
//    private long userID = 123456;  // User ID (you can retrieve this from the user’s session or profile)
//    private long songID = 789;  // The song ID the user is interacting with
//    private int rank = 1;  // Rank of the song
//    private String updatedTime = "2024-11-05T12:34:56";  // The current time of the update (you can use `LocalDateTime.now()` to get the current time)
//
//    public LeaderboardWebSocketService(LinearLayout leaderboardLayout) {
//        this.leaderboardLayout = leaderboardLayout;
//        connectToWebSocket();
//    }
//
//    private void connectToWebSocket() {
//        try {
//            URI uri = new URI(WEBSOCKET_URL);
//            webSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("WebSocket", "Connected to WebSocket");
//                    sendLeaderboardData();
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    Log.d("WebSocket", "Message received: " + message);
//                    // Optionally handle messages received from the backend
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("WebSocket", "Disconnected from WebSocket");
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("WebSocket", "Error: " + ex.getMessage());
//                }
//            };
//            webSocketClient.connect();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Method to send leaderboard data in the required format
//    private void sendLeaderboardData() {
//        try {
//            JSONObject leaderboardData = new JSONObject();
//            leaderboardData.put("leaderboardID", leaderboardID);
//            leaderboardData.put("category", category);
//            leaderboardData.put("userID", userID);
//            leaderboardData.put("songID", songID);
//            leaderboardData.put("rank", rank);
//            leaderboardData.put("updatedTime", updatedTime);
//
//            // Send the JSON object as a message to the backend
//            webSocketClient.send(leaderboardData.toString());
//            Log.d("WebSocket", "Sent leaderboard data: " + leaderboardData.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Update the leaderboard UI (if needed)
//    private void updateLeaderboardUI(String message) {
//        // Handle leaderboard updates if needed.
//        // You can update the UI with a new leaderboard message.
//    }
//}
