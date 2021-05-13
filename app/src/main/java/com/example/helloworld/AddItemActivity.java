package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.example.helloworld.screens.main.BudgetFragment.ARG_PRICE;
import static com.example.helloworld.screens.main.BudgetFragment.ARG_TITLE;
import static com.example.helloworld.screens.main.BudgetFragment.REQUEST_CODE;

public class AddItemActivity extends AppCompatActivity {

    private EditText title;
    private EditText price;
    private Button addButton;

    private String mTitle, mPrice;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        title = findViewById(R.id.title);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTitle = s.toString();
                checkEditTextHasText();

            }
        });

        price = findViewById(R.id.price);
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPrice = s.toString();
                checkEditTextHasText();

            }
        });

        setInputTextColor();

        addButton = findViewById(R.id.add_button);

        configureButton();
    }

    private void configureButton() {
        addButton.setOnClickListener(v -> {
            mTitle = title.getText().toString();
            mPrice = price.getText().toString();
            if (mTitle.isEmpty() || mPrice.isEmpty()) {
                Toast.makeText(getApplicationContext(), getString(R.string.invalid_input), Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent();
            intent.putExtra(ARG_TITLE, mTitle);
            intent.putExtra(ARG_PRICE, mPrice);
            setResult(REQUEST_CODE, intent);
            finish();


        });
    }

    public void checkEditTextHasText() {
        addButton.setEnabled(!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mPrice));

    }
    public void setInputTextColor() {
        int activeFragmentIndex = getIntent().getIntExtra(ARG_TITLE, -1);
        if (activeFragmentIndex == 1) {
            title.setTextColor(ContextCompat.getColor(title.getContext(), R.color.green));
            price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.green));
//            price.setHintTextColor(ContextCompat.getColor(title.getContext(), R.color.green_hint));
//            title.setHintTextColor(ContextCompat.getColor(title.getContext(), R.color.green_hint));
        }
        title.setTextColor(ContextCompat.getColor(title.getContext(), R.color.colorPrimary));
        price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.colorPrimary));


    }
}
