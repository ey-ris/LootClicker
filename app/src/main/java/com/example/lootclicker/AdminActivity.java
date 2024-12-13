package com.example.lootclicker;

import static com.example.lootclicker.MainActivity.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.databinding.ActivityAdminBinding;
import com.example.lootclicker.databinding.ActivityMainBinding;
import com.example.lootclicker.viewHolders.PlayerAdapter;
import com.example.lootclicker.viewHolders.PlayerViewModel;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private AppRepository repository;

    private PlayerViewModel playerViewModel;
    private int selectedUserId;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        RecyclerView recyclerView = binding.playerDisplayRecyclerView;
        final PlayerAdapter adapter = new PlayerAdapter(new PlayerAdapter.PlayerDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = AppRepository.getRepository(getApplication());

        playerViewModel.getAllPlayers().observe(this, players -> {
            adapter.submitList(players);
        });

        binding.banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetInformationFromDisplay();
                banPlayer();
            }
        });
    }
    private void GetInformationFromDisplay(){
        try{
            selectedUserId = Integer.parseInt(binding.chosenPlayerEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error getting userID from edit text.");
        }
    }

    private void banPlayer(){
        if(selectedUserId < 1) {
            return;
        }
        repository.banPlayerById(selectedUserId);
        Toast.makeText(this, "BANNED!", Toast.LENGTH_SHORT).show();

        binding.chosenPlayerEditText.setText("");
        selectedUserId = -1;
    }

    static Intent adminActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }
}