package com.example.helloworld;

import android.app.Application;

import com.example.helloworld.remote.AuthApi;
import com.example.helloworld.remote.MoneyApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp extends Application {

    public static String AUTH_KEY = "authKey";

    public MoneyApi moneyApi;
    public AuthApi authApi;

    @Override
    public void onCreate() {
        super.onCreate();

        configureRetrofit();
    }

    private void configureRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://loftschool.com/android-api/basic/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        moneyApi = retrofit.create(MoneyApi.class);
        authApi = retrofit.create(AuthApi.class);


    }
}
