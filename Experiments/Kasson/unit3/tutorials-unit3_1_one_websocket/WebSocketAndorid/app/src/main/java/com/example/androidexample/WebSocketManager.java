package com.example.androidexample;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketManager {
    private static WebSocketManager instance;
    private WebSocketClient webSocketClient;
    private WebSocketListener listener;

    private WebSocketManager() {}

    public static synchronized WebSocketManager getInstance() {
        if (instance == null) {
            instance = new WebSocketManager();
        }
        return instance;
    }

    public void connectWebSocket(String serverUrl) {
        try {
            URI uri = new URI(serverUrl);
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    if (listener != null) {
                        listener.onWebSocketOpen(handshakedata);
                    }
                }

                @Override
                public void onMessage(String message) {
                    if (listener != null) {
                        listener.onWebSocketMessage(message);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    if (listener != null) {
                        listener.onWebSocketClose(code, reason, remote);
                    }
                }

                @Override
                public void onError(Exception ex) {
                    if (listener != null) {
                        listener.onWebSocketError(ex);
                    }
                }
            };
            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWebSocketListener(WebSocketListener listener) {
        this.listener = listener;
    }

    public void sendMessage(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(message);
        }
    }
}
