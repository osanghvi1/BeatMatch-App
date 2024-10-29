package com.example.androidexample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

public class TrendingSongWorker extends Worker {

    public TrendingSongWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // URL for the trending song API
        String url = "https://api.beatmatch.com/trending-song";

        // Create a map for any additional parameters if needed
        Map<String, String> params = new HashMap<>();
        // Add parameters if necessary, for example:
        // params.put("key", "value");

        // Use VolleySingleton to get trending song data
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(new MultipartRequest(
                Request.Method.POST, // HTTP method
                url, // The API URL
                null, // Image byte array, null if not uploading an image
                params, // Additional parameters map
                response -> {
                    // Parse response to get song title (assuming JSON format)
                    String songTitle = parseSongTitle(response);
                    if (isNewTrendingSong(songTitle)) {
                        // Show notification if there is a new trending song
                        MainActivity.showTrendingSongNotification(getApplicationContext(), songTitle);
                    }
                },
                error -> {
                    // Handle error (optional logging or retry strategy)
                    Log.e("TrendingSongWorker", "Error: " + error.getMessage());
                }
        ));

        // Return success, but consider returning Result.retry() in case of errors
        return Result.success();
    }

    private String parseSongTitle(String response) {
        // Extract the song title from the response (simplified here)
        // For example, if the response is a JSON string, use a JSON parser.
        return response; // Update this logic to parse JSON correctly
    }

    private boolean isNewTrendingSong(String songTitle) {
        // Implement logic to track changes in trending song title
        return true; // Placeholder - update based on your app logic
    }
}
