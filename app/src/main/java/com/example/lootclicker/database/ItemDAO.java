package com.example.lootclicker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lootclicker.database.entities.Item;

import java.util.List;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Item... item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE)
    List<Item> getAllItems();

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE id == :id LIMIT 1")
    LiveData<Item> getItemById(int id);

    @Query("DELETE FROM " + AppDatabase.ITEM_TABLE)
    void deleteAll();
}
