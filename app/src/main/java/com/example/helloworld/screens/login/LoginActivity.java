package com.example.helloworld.screens.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.LoftApp;
import com.example.helloworld.R;

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


    }
}
