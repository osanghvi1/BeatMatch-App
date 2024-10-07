//package com.example.androidexample;
//
//import android.content.Context;
//import android.media.MediaPlayer;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
//
//    private List<DeezerResponse.Track> songList;
//    private Context context;
//
//    public SongAdapter(Context context, List<DeezerResponse.Track> songList) {
//        this.context = context;
//        this.songList = songList;
//    }
//
//    @NonNull
//    @Override
//    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
//        return new SongViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
//        DeezerResponse.Track track = songList.get(position);
//        holder.songTitle.setText(track.getTitle());
//        holder.songArtist.setText(track.getArtist());
//        holder.songAlbum.setText(track.getAlbum());
//
//        holder.playButton.setOnClickListener(v -> {
//            // Implement media playback here
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            try {
//                mediaPlayer.setDataSource(track.getPreview()); // Assuming getPreview() returns a valid audio URL
//                mediaPlayer.prepare(); // might take time, consider using prepareAsync()
//                mediaPlayer.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return songList.size();
//    }
//
//    public static class SongViewHolder extends RecyclerView.ViewHolder {
//        TextView songTitle, songArtist, songAlbum;
//        Button playButton;
//
//        public SongViewHolder(View itemView) {
//            super(itemView);
//            songTitle = itemView.findViewById(R.id.songTitle);
//            songArtist = itemView.findViewById(R.id.songArtist);
//            songAlbum = itemView.findViewById(R.id.songAlbum);
//            playButton = itemView.findViewById(R.id.playButton);
//        }
//    }
//}