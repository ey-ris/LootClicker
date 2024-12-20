package com.example.lootclicker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lootclicker.database.entities.User;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Delete
    void delete(User user);

    @Query(" SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY username")
    LiveData<User> getAllUsers();

    @Query("DELETE from " + AppDatabase.USER_TABLE)
    void deleteAll();

    @Query(" SELECT * from " + AppDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query(" SELECT * from " + AppDatabase.USER_TABLE + " WHERE userId == :userId")
    LiveData<User> getUserByUserId(int userId);
}
