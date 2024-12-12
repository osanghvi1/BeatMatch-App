package com.example.openingactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<String> {

    public MessageAdapter(@NonNull Context context, @NonNull List<String> messages) {
        super(context, 0, messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        }

        String message = getItem(position);
        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        return convertView;
    }
}

