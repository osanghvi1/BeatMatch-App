package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SwipingActivity extends AppCompatActivity implements Request {

    ExecutorService executorService;

    private ArrayAdapter<String> adapter;
    List<String> data;
    SwipeFlingAdapterView flingAdapterView;

    public String getNewSong(int k) {

        //Make request to Deezer for a song

        //Check with backend Liked songs Table AND Disliked songs table to see if we've seen it already

        //if we have, try again

        //if we have not, return the queue


        String song = "Song " + k;
        return song;
    }

    public void createSongQueue() {
        int k=0;
        while(k<=5){
            data.add(getNewSong(k));
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

        data=new ArrayList<>();

        createSongQueue();


        adapter = new ArrayAdapter<>(SwipingActivity.this, R.layout.item, R.id.textSongData, data);

        flingAdapterView.setAdapter(adapter);

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                data.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(SwipingActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(SwipingActivity.this, "Like", Toast.LENGTH_SHORT).show();
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
