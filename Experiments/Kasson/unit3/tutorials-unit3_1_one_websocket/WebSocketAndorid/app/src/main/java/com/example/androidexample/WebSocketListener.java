package com.example.androidexample;

import org.java_websocket.handshake.ServerHandshake;

public interface WebSocketListener {
    void onWebSocketMessage(String message);
    void onWebSocketClose(int code, String reason, boolean remote);
    void onWebSocketOpen(ServerHandshake handshakedata);
    void onWebSocketError(Exception ex);
}
