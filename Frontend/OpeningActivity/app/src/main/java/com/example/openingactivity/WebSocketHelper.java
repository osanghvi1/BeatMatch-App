package com.example.openingactivity;

import android.os.Handler;

import org.java_websocket.client.WebSocketClient;

public class WebSocketHelper {
    private static final int RECONNECT_DELAY = 5000; // 5 seconds
    private WebSocketClient webSocketClient;
    private Handler handler = new Handler();

    public WebSocketHelper(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public void reconnect() {
        handler.postDelayed(() -> {
            if (!webSocketClient.isOpen()) {
                webSocketClient.reconnect();
            }
        }, RECONNECT_DELAY);
    }
}
