package com.example.lootclicker.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lootclicker.MainActivity;
import com.example.lootclicker.database.entities.Item;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.database.entities.User;
import com.example.lootclicker.database.typeConverters.EquipmentConverter;
import com.example.lootclicker.database.typeConverters.InventoryConverter;
import com.example.lootclicker.database.typeConverters.ItemModifiersConverter;
import com.example.lootclicker.database.typeConverters.ItemTypeConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@TypeConverters(LocalDateTypeConverter.class)
@TypeConverters({
        ItemModifiersConverter.class,
        ItemTypeConverter.class,
        EquipmentConverter.class,
        InventoryConverter.class
})
@Database(entities = {User.class, Player.class, Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "usertable";
    public static final String PLAYER_TABLE = "player_table";
    public static final String ITEM_TABLE = "item_table";
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
                ItemDAO itemDAO = INSTANCE.itemDao();


                userDAO.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                playerDAO.insert(createPlayer(admin.getUserId()));
                userDAO.insert(admin);

                User testUser1 = new User("testUser1", "testUser1");
                playerDAO.insert(createPlayer(testUser1.getUserId()));
                userDAO.insert(testUser1);
            });
        }
    };

    private static Player createPlayer(int userId){
        return new Player(0,1,0,0,new HashMap<>(),new ArrayList<>(), userId);
    }

    public abstract UserDAO userDao();

    public abstract PlayerDAO playerDao();

    public abstract ItemDAO itemDao();
}