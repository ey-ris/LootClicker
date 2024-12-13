package com.example.lootclicker.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.lootclicker.database.entities.Player;

public class PlayerAdapter  extends ListAdapter<Player, PlayerViewHolder> {

    public PlayerAdapter(@NonNull DiffUtil.ItemCallback<Player> playerCallback){
        super(playerCallback);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PlayerViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player current = getItem(position);
        holder.bind(current.toString());
    }

    public static class PlayerDiff extends DiffUtil.ItemCallback<Player>{
        @Override
        public boolean areItemsTheSame(@NonNull Player oldPlayer, @NonNull Player newPlayer){
            return oldPlayer == newPlayer;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Player oldPlayer, @NonNull Player newPlayer){
            return oldPlayer.equals(newPlayer);
        }
    }
}
