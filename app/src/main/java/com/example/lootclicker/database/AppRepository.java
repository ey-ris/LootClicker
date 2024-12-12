package com.example.lootclicker.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.lootclicker.MainActivity;
import com.example.lootclicker.database.entities.Item;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private final PlayerDAO playerDAO;
    private final ItemDAO itemDAO;
    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDao();
        this.playerDAO = db.playerDao();
        this.itemDAO = db.itemDao();
    }

    public static AppRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting AppRepository, thread error.");
        }
        return null;
    }

    //-=-=-=-=-=-=-=-User-=-=-=-=-=-=-=-
    public void insertUser(User... user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
                    userDAO.insert(user);
                }
        );
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }


    //-=-=-=-=-=-=-=-Player-=-=-=-=-=-=-=-
    public void insertPlayer(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDAO.insert(player);
        });
    }

    public List<Player> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }

    public LiveData<Player> getPlayerByUserId(int userId) {
        return playerDAO.getPlayerByUserId(userId);
    }

    //-=-=-=-=-=-=-=-Items-=-=-=-=-=-=-=-

    public void insertItem(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            itemDAO.insert(item);
        });
    }

    public void deleteItem(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            itemDAO.delete(item);
        });
    }

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public LiveData<Item> getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }
}