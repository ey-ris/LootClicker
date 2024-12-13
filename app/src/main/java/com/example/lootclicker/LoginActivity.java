package com.example.lootclicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.User;
import com.example.lootclicker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.signupButtonLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SignupActivity.signupIntentFactory(getApplicationContext()));
            }
        });
    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this,"Username may not be blank.", Toast.LENGTH_SHORT).show();

            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    if(user.isAdmin()){
                        startActivity(AdminActivity.adminActivityIntentFactory(getApplicationContext()));
                    }else{
                        startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserId()));
                    }
                } else {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();

                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                Toast.makeText(this, String.format("%s is not a valid username.", username), Toast.LENGTH_SHORT).show();

                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}