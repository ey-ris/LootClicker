package com.example.lootclicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.database.entities.User;
import com.example.lootclicker.databinding.ActivityMainBinding;

import java.util.Random;

/*
    Sam Numan
    Last update: 12/10/24
    This class is the Main activity holding the game's main functions.

    Dakota Hyman
    Last update: 12/12/24
    Wired up the clicker button to update the player table, and receive boosts and deal critical damage.
*/

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.lootclicker.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;
    private AppRepository repository;
    private User user;
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
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

        grabPlayerFromDatabase();

        //Main clicking structure of the game
        binding.mainClickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayerClick();
            }
        });
    }

    void onPlayerClick() {
        if (player != null) {

            calculateClick();

            //Update Currency
            binding.currencyCountTextView.setText(String.format("%s", player.getCurrency()));
        }
    }

    private void calculateClick() {
        long currency = player.getCurrency();
        double clickStrength = player.getClickStrength();

        Random rand = new Random();
        double luckyStrike = rand.nextDouble();
        double critStrike = rand.nextDouble();

        if(player.getCritChance() >= critStrike){
            if(player.getCritChance() > 1.0){
                //If crit is over 100%,then add the remainder crit strength
                clickStrength = clickStrength * (3d + (player.getCritChance()-1));
            }else{
                clickStrength = clickStrength * 3d;
            }
        }

        if(player.getLuckyStrike() >= luckyStrike){
            getRandomBoost();
        }

        currency += (long)clickStrength;

        player.setCurrency(currency);
        repository.updatePlayer(player);
    }

    private void getRandomBoost(){
        Random random = new Random();
        double num = random.nextDouble();
        if(num > 0.9){//
            player.setLuckyStrike(player.getLuckyStrike() + 0.01);
            Toast.makeText(this, "You found a lucky item.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(num > 0.55){
            player.setCritChance(player.getCritChance() + 0.025);
            Toast.makeText(this, "You learned a new technique.", Toast.LENGTH_SHORT).show();
            return;
        }
        player.setClickStrength(player.getClickStrength() + 1.5);
        Toast.makeText(this, "You found a new sword.", Toast.LENGTH_SHORT).show();
    }


    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void loginUser(Bundle savedInstanceState) {

        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
//            return;
        }
    }

    private void grabPlayerFromDatabase() {
        LiveData<Player> playerObserver = repository.getPlayerByUserId(loggedInUserId);
        playerObserver.observe(this, player -> {
            if (player != null) {
                this.player = player;
            }
        });

        if (!repository.playerExists(loggedInUserId)) {
            player = new Player(0, 1, 0, 0, loggedInUserId);
            repository.updatePlayer(player);
        }
    }
}