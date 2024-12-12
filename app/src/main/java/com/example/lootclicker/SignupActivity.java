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
import com.example.lootclicker.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Sam Numan
    Last update: 12/10/24
    This class implements the sign up functionality using the sign up screen and user database
 */

public class SignupActivity extends AppCompatActivity {
    
    private ActivitySignupBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserAccount();
            }
        });

        binding.loginHighlightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });
    }

    private void createUserAccount() {
        String username = binding.userNameSignupEditText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this,"Username may not be blank.", Toast.LENGTH_SHORT).show();

            return;
        }

        // Used try-catch for testing because userObserver was throwing a NullPointerException error
        try {
            LiveData<User> userObserver = repository.getUserByUserName(username);
            userObserver.observe(this, user -> {
                if (user != null) {
                    Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show();
                    binding.userNameSignupEditText.setSelection(0);
                } else {
                    String password = binding.passwordSignupEditText.getText().toString();
                    user = new User(username, password);
//                    repository.insertUser(user);
                    Player newPlayer = new Player(0,1,0,0,new HashMap<>(), new ArrayList<>(), -1);
                    repository.insertUserAndPlayer(user, newPlayer);

                    Toast.makeText(this, "User Added - proceed to login", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Null user");
            Toast.makeText(this, "Null User", Toast.LENGTH_SHORT).show();
        }
    }

    static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}