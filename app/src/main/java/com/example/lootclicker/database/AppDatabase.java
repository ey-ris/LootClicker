package com.example.lootclicker.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lootclicker.MainActivity;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Sam Numan
    Last update: 12/10/24
    This is the application's database that stores multiple tables including a user table for logging in and signing up

    Dakota Hyman
    Last update: 12/12/24
    Added new tables for players and items
        The item table commented out due to project re-scoping
 */

@Database(entities = {User.class, Player.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "usertable";
    public static final String PLAYER_TABLE = "player_table";
    //public static final String ITEM_TABLE = "item_table";
    private static final String DATABASE_NAME = "LootClickerDatabase";

    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = INSTANCE.userDao();
                PlayerDAO playerDAO = INSTANCE.playerDao();


                userDAO.deleteAll();
                playerDAO.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                userDAO.insert(admin);

                Player player = new Player(0,1,0.01,0.05, 1);
                playerDAO.insert(player);

                User testUser1 = new User("testUser1", "testUser1");
                userDAO.insert(testUser1);
                Player player2 = new Player(0,1,0.01,0.05, 2);
                playerDAO.insert(player2);
            });
        }
    };

    public abstract UserDAO userDao();

    public abstract PlayerDAO playerDao();
}