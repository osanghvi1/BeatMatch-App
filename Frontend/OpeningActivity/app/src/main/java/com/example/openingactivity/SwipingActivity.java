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
    private String imageLink;
    private String previewLink;

    public Song(String title, String artist, String imageLink, String previewLink) {
        this.title = title;
        this.artist = artist;
        this.imageLink = imageLink;
        this.previewLink = previewLink;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    @Override
    public String toString() {
        return title + '\n' + artist;
    }
}


public class SwipingActivity extends AppCompatActivity implements Request {

    ExecutorService executorService;

    private ArrayAdapter<Song> adapter;

    List<Song> songData;

    SwipeFlingAdapterView flingAdapterView;

    private int getRandomID() {
        int IDtag = new Random().nextInt(5000000) + 600000;
        // Return the generated random
        return IDtag;
    }

    private void addDeezerSong() {
        // Implement the logic to fetch a song
        // URL for the Deezer Song Request
        String deezerSongUrl = "https://api.deezer.com/track/" + getRandomID();

        // send request to Deezer
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String result = sendDeezerRequest("GET", deezerSongUrl);

                    //parse the JSON body to return song info
                    JSONObject json = new JSONObject(result);
                    String title = json.getString("title");
                    String artist = json.getJSONArray("contributors").getJSONObject(0).getString("name");

                    Song song = new Song(title, artist, "image", "preview");
                    songData.add(song);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void createSongQueue() {
        int k=0;
        while(k<=5){
            addDeezerSong();
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

        songData=new ArrayList<>();

        createSongQueue();


        adapter = new ArrayAdapter<Song>(SwipingActivity.this, R.layout.item, R.id.textSongData, songData);

        flingAdapterView.setAdapter(adapter);


        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                songData.remove(0);
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
                Toast.makeText(SwipingActivity.this, "Item Clicked " + songData.get(i), Toast.LENGTH_SHORT).show();
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
