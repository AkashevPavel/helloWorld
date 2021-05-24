package com.example.helloworld.screens.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.helloworld.remote.AuthApi;
import com.example.helloworld.remote.AuthResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<String> messageString = new MutableLiveData<>();
    public MutableLiveData<String> authToken = new MutableLiveData<>();


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void makeLogin(AuthApi authApi){
        String userId = "1111";

        compositeDisposable.add(authApi.makeLogin(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(authResponse -> {
                authToken.postValue(authResponse.getAuth_token());
            }, throwable -> {
                messageString.postValue(throwable.getLocalizedMessage());
            }));

    }
}
