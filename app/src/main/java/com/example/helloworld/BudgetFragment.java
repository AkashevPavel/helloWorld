package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.items.Item;
import com.example.helloworld.items.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private RecyclerView itemsView;
    private ItemsAdapter itemsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);
        itemsView = view.findViewById(R.id.itemsView);
        itemsAdapter = new ItemsAdapter();
        itemsView.setAdapter(itemsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        itemsView.setLayoutManager(layoutManager);
        itemsAdapter.setData(generateItemsList());

        return view;
    }

    public List<Item> generateItemsList(){
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item("milk", "40"));
        itemsList.add(new Item("toothbrush", "70"));
        itemsList.add(new Item("salary", "100"));

        return itemsList;
    }


}
