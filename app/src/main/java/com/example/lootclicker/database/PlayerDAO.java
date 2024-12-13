package com.example.lootclicker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lootclicker.database.entities.Player;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Player player);

    @Query("DELETE FROM " + AppDatabase.PLAYER_TABLE)
    void deleteAll();

    @Update
    void update(Player player);

    @Query("SELECT * FROM " + AppDatabase.PLAYER_TABLE + " ORDER BY currency DESC")
    LiveData<List<Player>> getAllPlayers();

    @Query("SELECT * FROM " + AppDatabase.PLAYER_TABLE + " WHERE userId == :userId LIMIT 1")
    LiveData<Player> getPlayerByUserId(int userId);

    @Query("UPDATE " + AppDatabase.PLAYER_TABLE + " SET currency = -9001, clickStrength = 0, critChance = -1.0, luckyStrike = -1.0 WHERE userId = :userId")
    void banUserById(int userId);
}
