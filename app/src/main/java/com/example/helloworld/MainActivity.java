package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.helloworld.cells.MoneyCellAdapter;
import com.example.helloworld.cells.MoneyItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsView;

    private MoneyCellAdapter moneyCellAdapter = new MoneyCellAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecyclerView();

        generateMoney();
    }

    private void generateMoney() {
        List<MoneyItem> moneyItems = new ArrayList<>();
        moneyItems.add(new MoneyItem("Corn flakes", "3000"));
        moneyItems.add(new MoneyItem("salary", "50000"));


        moneyCellAdapter.setData(moneyItems);
    }

    private void configureRecyclerView() {
        itemsView = findViewById(R.id.itemsView);
        itemsView.setAdapter(moneyCellAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        itemsView.setLayoutManager(layoutManager);
    }
}