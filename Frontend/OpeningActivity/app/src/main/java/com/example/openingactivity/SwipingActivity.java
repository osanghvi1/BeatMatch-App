package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Song {
    private String title;
    private String artist;
    private String albumCoverLink;
    private String previewLink;

}


public class SwipingActivity extends AppCompatActivity implements Request {

    ExecutorService executorService;

    private ArrayAdapter<String> adapter;
    List<String> titleData;
    List<String> artistData;
    List<String> imageData;
    List<String> previewData;


    SwipeFlingAdapterView flingAdapterView;

    private int getRandomID() {
        int IDtag = new Random().nextInt(60000000) + 600000;
        // Return the generated random
        return IDtag;
    }

    private Song AddDeezerSong() {
        // Implement the logic to fetch a song
        // URL for the Deezer Song Request
        String deezerSongUrl = "https://api.deezer.com/track/" + getRandomID();

        JsonObjectRequest songRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, deezerSongUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject titleData = response.getJSONObject("title");
                            JSONObject artistData = response.getJSONObject("artist");
                            JSONObject imageData = response.getJSONObject("album");
                            JSONObject previewData = response.getJSONObject("preview");

                            String artist = artistData.toString();
                            String album = imageData.toString();
                            String preview = previewData.toString();
                            String title = titleData.toString();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SwipingActivity.this, "Error fetching genres", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(GenrePreferences.this, "Failed to fetch Deezer genres", Toast.LENGTH_SHORT).show();
                    }
                });


        return null;
    }


    public String getNewSong(int k) {

        //Make request to Deezer for a song
        AddDeezerSong();

        //Check with backend Liked songs Table AND Disliked songs table to see if we've seen it already

        //if we have, try again

        //if we have not, add to the queue


        String song = "Song " + k;
        return song;
    }

    public void createSongQueue() {
        int k=0;
        while(k<=5){
            titleData.add(getNewSong(k));
            k++;
        }
    }

    //onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping);
        executorService = Executors.newSingleThreadExecutor();

        flingAdapterView = findViewById(R.id.swipe);

        titleData=new ArrayList<>();
        artistData=new ArrayList<>();
        previewData=new ArrayList<>();
        imageData=new ArrayList<>();

        createSongQueue();


        adapter = new ArrayAdapter<>(SwipingActivity.this, R.layout.item, R.id.textSongData, data);

        flingAdapterView.setAdapter(adapter);

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                titleData.remove(0);
                artistData.remove(0);
                imageData.remove(0);
                previewData.remove(0);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(SwipingActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
                // Add to disliked songs
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(SwipingActivity.this, "Like", Toast.LENGTH_SHORT).show();
                // Add to liked songs
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                createSongQueue();
            }

            @Override
            public void onScroll(float v) {

            }
        });


        flingAdapterView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int i, Object o) {
                Toast.makeText(SwipingActivity.this, "Item Clicked " + data.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        Button LikeButton, DislikeButton, RefreshButton;

        LikeButton = findViewById(R.id.like);
        DislikeButton = findViewById(R.id.dislike);
        RefreshButton = findViewById(R.id.refresh);

        LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingAdapterView.getTopCardListener().selectRight();
            }
        });

        DislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingAdapterView.getTopCardListener().selectLeft();
            }
        });

        RefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSongQueue();
            }
        });


    }
}
