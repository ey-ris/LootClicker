package com.example.lootclicker;

import static com.example.lootclicker.MainActivity.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.Player;
import com.example.lootclicker.databinding.ActivityAdminBinding;
import com.example.lootclicker.viewHolders.PlayerAdapter;
import com.example.lootclicker.viewHolders.PlayerViewModel;

/**
 * This class is the landing page for the Admins
 * It shows all players and their stats
 * and gives the option to ban any player
 * Banning merely prevents them from growing or continuing.
 *
 *  Dakota Hyman
 *  Last update: 12/13/24
 * */

public class AdminActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.lootclicker.MAIN_ACTIVITY_USER_ID";
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        item.setTitle("Admin");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), -1);
        startActivity(intent);
    }

    static Intent adminActivityIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }
}