package com.example.lootclicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.database.entities.User;
import com.example.lootclicker.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.lootclicker.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;
    private AppRepository repository;
    private User user;
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    public int userCurrency = 0;
    public static final String TAG = "SEAQUENCE_GYMLOG";
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        //No user, go to login
        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        //Main clicking structure of the game
        binding.mainClickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player == null)return;
                updateUserCurrencyCount();
            }
        });
    }

    void updateUserCurrencyCount() {
        userCurrency++;
        binding.currencyCountTextView.setText(String.format("%s", userCurrency));
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void loginUser(Bundle savedInstanceState) {

        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);

        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
            return;
        }

        //Set player up
        LiveData<Player> playerObserver = repository.getPlayerByUserId(loggedInUserId);
        playerObserver.observe(this, player -> {
            this.player = player;
        });

        //TODO add sharedpreferences to save login
    }
}