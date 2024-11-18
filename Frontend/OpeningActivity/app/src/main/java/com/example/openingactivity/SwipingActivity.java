package com.example.openingactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Song {
    private String title;
    private String artist;
    private String imageLink;
    private String previewLink;
    private String id;

    public Song(String title, String artist, String imageLink, String previewLink, String id) {
        this.id = id;
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
    public String getID() {return id;}

    @Override
    public String toString() {
        String titleB;
        String artistB;

        if (title.toCharArray().length > 60) {
           titleB = title.substring(0, 20).concat("...");
        } else {
            titleB = title;
        }

        if (artist.toCharArray().length > 60) {
            artistB = artist.substring(0, 20).concat("...");
        } else {
            artistB = artist;
        }

        return titleB + '\n' + artistB;
    }
}


public class SwipingActivity extends AppCompatActivity implements Request {

    ExecutorService executorService;

    private ArrayAdapter<Song> adapter;


    List<Song> songData;

    SwipeFlingAdapterView flingAdapterView;

    /**
     * Generates a random id used for song finding
     * @return random id
     */
    private int getRandomID() {
        int IDtag = new Random().nextInt(5000000) + 600000;
        // Return the generated random
        return IDtag;
    }

    /**
     * Adds a song to the song queue
     *
     */
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
                    String album = json.getJSONObject("album").getString("title");
                    String preview = json.getString("preview");
                    String id = json.getString("id");

                    Song song = new Song(title, artist, album, preview, id);
                    songData.add(song);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Helper method to create mutliple Deezer song objects and add them to the queue
     */
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

        songData.add(new Song("Heres your next listen ...", "", "album", "preview", null));
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
                System.out.println(o);
                Toast.makeText(SwipingActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
                // Add to disliked songs
                Song song = (Song) o;
                String songID = song.getID();
                String songTitle = song.getTitle();

                JSONObject json = new JSONObject();


                String genre = getGenre();
                try {
                    json.put("songTitle", songTitle);
                    json.put("songID", songID);
                    json.put("genre",genre);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Send POST request
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String response = sendRequest("POST", "/users/dislikeSong/" + user.getUserID(), json.toString());
                        System.out.println(response);
                    }
                });
            }

            @Override
            public void onRightCardExit(Object o) {
                System.out.println(o);
                Toast.makeText(SwipingActivity.this, "Like", Toast.LENGTH_SHORT).show();
                // Add to liked songs
                Song song = (Song) o;
                String songID = song.getID();
                String songTitle = song.getTitle();

                JSONObject json = new JSONObject();

                String genre = getGenre();
                try {
                    json.put("songID", songID);
                    json.put("songTitle", songTitle);
                    json.put("genre",genre);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Send POST request
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String response = sendRequest("POST", "/users/likeSong/" + user.getUserID(), json.toString());
                        System.out.println(response);
                    }
                });

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
                songData.add(new Song("Heres something new ...", "", "album", "preview", "id"));
                createSongQueue();
            }
        });

    }

    /**
     * Generates a random genre USED FOR TESTING
     * COMMENT FOR TESTING 4
     * @return random genre
     */
    public String getGenre() {
        String genre = "";
        Random rand = new Random();
        int a = rand.nextInt(4);
        if (a == 0) {
            genre = "pop";
        } else if (a == 1) {
            genre = "rock";
        } else if (a == 2) {
            genre = "hiphop";
        } else if (a == 3) {
            genre = "classical";
        }
        return genre;
    }
}
