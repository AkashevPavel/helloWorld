package com.example.helloworld.screens.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.LoftApp;
import com.example.helloworld.R;
import com.example.helloworld.screens.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configureView();
        configureViewModel();
    }

    private void configureView() {
        AppCompatButton appCompatButton = findViewById(R.id.btn_enter);

        appCompatButton.setOnClickListener(v -> {
            loginViewModel.makeLogin(((LoftApp) getApplication()).authApi);
        });
    }

    private void configureViewModel() {

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.authToken.observe(this, token -> {
            if (!TextUtils.isEmpty(token)) {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0);
                sharedPreferences.edit().putString(LoftApp.AUTH_KEY, token).apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginViewModel.messageString.observe(this, message -> {
            if (!TextUtils.isEmpty(message)) Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });


    }
}
