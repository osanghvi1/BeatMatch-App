//package com.example.leaderboards;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
//
//    private List<Song> songList;
//
//    public SongAdapter(List<Song> songList) {
//        this.songList = songList;
//    }
//
//    @Override
//    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
//        return new SongViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(SongViewHolder holder, int position) {
//        Song song = songList.get(position);
//        holder.songTitleTextView.setText(song.getSongTitle());
//        holder.rankTextView.setText(String.valueOf(song.getRank()));
//        holder.songIDTextView.setText(String.valueOf(song.getSongID()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return songList.size();
//    }
//
//    public static class SongViewHolder extends RecyclerView.ViewHolder {
//        TextView songTitleTextView;
//        TextView rankTextView;
//        TextView songIDTextView;
//
//        public SongViewHolder(View itemView) {
//            super(itemView);
//            songTitleTextView = itemView.findViewById(R.id.songTitleTextView);
//            rankTextView = itemView.findViewById(R.id.rankTextView);
//            songIDTextView = itemView.findViewById(R.id.songIDTextView);
//        }
//    }
//}
//
