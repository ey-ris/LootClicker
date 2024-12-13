package com.example.lootclicker.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lootclicker.R;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

    private final TextView playerViewItem;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        playerViewItem = itemView.findViewById(R.id.recyclerItemTextView);
    }

    public static PlayerViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_recycler_item, parent, false);
        return new PlayerViewHolder(view);
    }

    public void bind(String text) {
        playerViewItem.setText(text);
    }
}
