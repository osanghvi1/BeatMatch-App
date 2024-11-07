package com.example.androidexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "beatmatch_trending_channel";
    private static final int NOTIFICATION_ID = 1;
    private WebSocket webSocket;
    private static final String SOCKET_URL = "ws://10.0.2.2:8080/chat"; // Replace with actual server address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Button enableNotificationsButton = findViewById(R.id.btnEnableNotifications);
        enableNotificationsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Notifications Enabled!", Toast.LENGTH_SHORT).show();
            startWebSocket();
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Trending Song Updates";
            String description = "Notifications for the top trending song on BeatMatch";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SOCKET_URL).build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                // Connected to WebSocket
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                // Handle the text message from server
                showTrendingSongNotification(MainActivity.this, text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                // Handle binary messages (optional, depending on your use case)
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(1000, null);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                // Handle error
                t.printStackTrace();
            }
        });
    }

    public static void showTrendingSongNotification(Context context, String songTitle) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon) // Ensure this is correctly referenced
                .setContentTitle("Top Trending Song")
                .setContentText("Check out the top trending song: " + songTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocket != null) {
            webSocket.close(1000, "Activity Destroyed");
        }
    }
}

