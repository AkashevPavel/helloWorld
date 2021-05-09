package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.helloworld.items.Item;
import com.example.helloworld.items.ItemsAdapter;
import com.example.helloworld.remote.MoneyRemoteItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 0;
    public static final String ARG_TITLE = "title";
    public static final String ARG_PRICE = "price";
    public static final String ARG_POSITION = "position";

    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private List<Item> itemsList = new ArrayList<>();
    private int currentPosition;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_POSITION);
        }
        loadItems();
    }



    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);

        recyclerView = view.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        itemsAdapter = new ItemsAdapter();
        recyclerView.setAdapter(itemsAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadItems();
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && data != null) {
            Disposable disposable = ((LoftApp) getActivity().getApplicationContext()).moneyApi.postMoney(
                    Double.parseDouble(data.getStringExtra(ARG_PRICE)), data.getStringExtra(ARG_TITLE), getTypeFromPosition(currentPosition))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Toast.makeText(getActivity(), getString(R.string.successfully_added), Toast.LENGTH_LONG).show(),
                            throwable -> Toast.makeText(getActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show());

            compositeDisposable.add(disposable);
        }


    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }


    private void loadItems() {
        Disposable disposable = ((LoftApp) getActivity().getApplication()).moneyApi.getMoneyItem(getTypeFromPosition(currentPosition))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    if (moneyResponse.getStatus().equals("success")) {
                        itemsList = new ArrayList<>();
                        for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemList()) {
                            itemsList.add(Item.getInstance(moneyRemoteItem));
                        }

                        itemsAdapter.setData(itemsList);

                    } else {
                        Toast.makeText(getActivity(), getString(R.string.connection_lost), Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    Toast.makeText(getActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });

        compositeDisposable.add(disposable);

    }

    private String getTypeFromPosition(int currentPosition) {
        String type;
        if (currentPosition == 0) type = "expense";
        else type = "income";
        return type;
    }
}
