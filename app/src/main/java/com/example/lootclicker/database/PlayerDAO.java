package com.example.lootclicker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lootclicker.database.entities.Player;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player... player);

    //Shows all players starting from richest
    @Query("SELECT * FROM " + AppDatabase.PLAYER_TABLE + " ORDER BY currency DESC")
    List<Player> getAllPlayers();

    @Query("SELECT * FROM " + AppDatabase.PLAYER_TABLE + " WHERE userId = :userId")
    LiveData<Player> getPlayerByUserId(int userId);
}