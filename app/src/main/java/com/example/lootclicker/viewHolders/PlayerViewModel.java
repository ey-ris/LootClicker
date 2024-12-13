package com.example.lootclicker.viewHolders;

import android.app.Application;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.Player;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public PlayerViewModel(Application application){
        super(application);

        repository = AppRepository.getRepository(application);
    }

    public LiveData<List<Player>> getAllPlayers(){
        return repository.getAllPlayers();
    }

    public void insert(Player player){
        repository.insertPlayer(player);
    }
}
