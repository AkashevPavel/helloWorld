package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.helloworld.items.Item;
import com.example.helloworld.items.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsView;
    private ItemsAdapter itemsAdapter = new ItemsAdapter(generateItemsList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecyclerView();
    }

    public List<Item> generateItemsList(){
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item("milk", "40"));
        itemsList.add(new Item("toothbrush", "70"));
        itemsList.add(new Item("salary", "100"));

        return itemsList;
    }

    private void configureRecyclerView() {
        itemsView = findViewById(R.id.itemsView);
        itemsView.setAdapter(itemsAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        itemsView.setLayoutManager(layoutManager);
    }


}