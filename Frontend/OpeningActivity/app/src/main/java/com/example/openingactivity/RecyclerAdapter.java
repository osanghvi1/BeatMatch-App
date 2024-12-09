package com.example.openingactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Event> eventList;

    public RecyclerAdapter(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView eventName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.textView_event_name);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String eventName = eventList.get(position).getEventName();

        holder.eventName.setText(eventName);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
