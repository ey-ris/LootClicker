package com.example.lootclicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lootclicker.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private int userCurrency = 0;
    public static final String TAG = "SEAQUENCE_GYMLOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        //Main clicking structure of the game
        binding.mainClickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCurrency++;
                updateUserCurrencyCount(userCurrency);
            }
        });
    }

    void updateUserCurrencyCount(int userCurrency){
        binding.currencyCountTextView.setText(userCurrency);
        Toast.makeText(this,userCurrency, Toast.LENGTH_SHORT).show();

    }
}