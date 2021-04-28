package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.helloworld.BudgetFragment.ARG_PRICE;
import static com.example.helloworld.BudgetFragment.ARG_TITLE;
import static com.example.helloworld.BudgetFragment.REQUEST_CODE;

public class AddItemActivity extends AppCompatActivity {

    EditText title;
    EditText price;
    Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(ARG_TITLE, title.getText().toString());
            intent.putExtra(ARG_PRICE, price.getText().toString());
            setResult(REQUEST_CODE, intent);
            finish();
        });
    }
}
