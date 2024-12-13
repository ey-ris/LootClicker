package com.example.lootclicker.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.lootclicker.MainActivity;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private final PlayerDAO playerDAO;
    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDao();
        this.playerDAO = db.playerDao();
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
    public void insertUser(User user) {
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

    public void insertUserAndPlayer(User user, Player player){
        AppDatabase.databaseWriteExecutor.execute(() ->{
            long id = userDAO.insert(user);
            player.setUserId((int)id);
            playerDAO.insert(player);
        });
    }


    //-=-=-=-=-=-=-=-Player-=-=-=-=-=-=-=-
    public void insertPlayer(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDAO.insert(player);
        });
    }

    public void deleteAllPlayers() {
        AppDatabase.databaseWriteExecutor.execute(playerDAO::deleteAll);
    }

    public void updatePlayer(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDAO.update(player);
        });
    }

    public LiveData<List<Player>> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }

    public void banPlayerById(int userId){
        AppDatabase.databaseWriteExecutor.execute(()->{
            Player player = getPlayerByUserId(userId).getValue();
            if(player == null) {
                Log.d(MainActivity.TAG, "Could not find player with userid");
                return;
            }
            player = new Player(-9001,0,0,0,userId);
            updatePlayer(player);
        });
    }

    public LiveData<Player> getPlayerByUserId(int userId) {
        return playerDAO.getPlayerByUserId(userId);
    }

    public boolean playerExists(int userId) {
        Player player = playerDAO.getPlayerByUserId(userId).getValue();
        return player != null;
    }
}