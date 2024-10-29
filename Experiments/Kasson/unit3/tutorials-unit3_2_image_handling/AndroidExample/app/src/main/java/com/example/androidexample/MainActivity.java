package com.example.androidexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "beatmatch_trending_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        // Schedule background work to check trending song periodically
        PeriodicWorkRequest trendingCheckRequest = new PeriodicWorkRequest.Builder(
                TrendingSongWorker.class, 1, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance(this).enqueue(trendingCheckRequest);

        Button enableNotificationsButton = findViewById(R.id.btnEnableNotifications);
        enableNotificationsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Notifications Enabled!", Toast.LENGTH_SHORT).show();
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

    public static void showTrendingSongNotification(Context context, String songTitle) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon) // Ensure this is correctly referenced
                .setContentTitle("Top Trending Song")
                .setContentText("Check out the top trending song: " + songTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request permission if not granted
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
