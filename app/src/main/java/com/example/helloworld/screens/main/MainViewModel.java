package com.example.helloworld.screens.main;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.helloworld.LoftApp;
import com.example.helloworld.R;
import com.example.helloworld.items.Item;
import com.example.helloworld.remote.MoneyApi;
import com.example.helloworld.remote.MoneyRemoteItem;
import com.example.helloworld.remote.MoneyResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> itemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadBudget(MoneyApi moneyApi, String type, SharedPreferences sharedPreferences) {

        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.getMoneyItem(type, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyRemoteItems -> {

                    List<Item> items = new ArrayList<>();

                    for (MoneyRemoteItem moneyRemoteItem : moneyRemoteItems) {
                        items.add(Item.getInstance(moneyRemoteItem));
                    }
                    itemsList.postValue(items);

                }, throwable -> messageString.postValue(throwable.getLocalizedMessage())));
    }

    public void uploadBudget(MoneyApi moneyApi, double price, String title, String type, SharedPreferences sharedPreferences) {

        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.postMoney(price, title, type, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    messageInt.postValue(R.string.successfully_added);
                }, throwable -> messageString.postValue(throwable.getLocalizedMessage())));
    }
}
