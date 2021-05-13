package com.example.helloworld.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.helloworld.LoftApp;
import com.example.helloworld.R;
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

    private ItemsAdapter itemsAdapter;
    private int currentPosition;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainViewModel mainViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, null);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        itemsAdapter = new ItemsAdapter();
        recyclerView.setAdapter(itemsAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            downLoad();
            swipeRefreshLayout.setRefreshing(false);
        });

        configureViewModel();
        downLoad();

        return view;
    }

    private void configureViewModel() {

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.itemsList.observe(getViewLifecycleOwner(), items -> itemsAdapter.setData(items));
        mainViewModel.messageString.observe(getViewLifecycleOwner(), message -> {
            if (!message.equals("")) showToast(message);
        });
        mainViewModel.messageInt.observe(getViewLifecycleOwner(), message -> {
            if (message > 0) showToast(getString(message));
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && data != null) {
            mainViewModel.uploadBudget(
                    ((LoftApp) getActivity().getApplicationContext()).moneyApi,
                    Double.parseDouble(data.getStringExtra(ARG_PRICE)),
                    data.getStringExtra(ARG_TITLE),
                    getTypeFromPosition(currentPosition),
                    getActivity().getSharedPreferences(getString(R.string.app_name), 0)
            );
        }
    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    private void downLoad() {
        mainViewModel.loadBudget(
                ((LoftApp) getActivity().getApplicationContext()).moneyApi,
                getTypeFromPosition(currentPosition),
                this.getActivity().getSharedPreferences(getString(R.string.app_name), 0));
    }

    private String getTypeFromPosition(int currentPosition) {
        String type;
        if (currentPosition == 0) type = "expense";
        else type = "income";
        return type;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
