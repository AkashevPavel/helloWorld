package com.example.helloworld.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoneyApi {

    @GET("./items")
    Single<List<MoneyRemoteItem>> getMoneyItem(@Query("type") String type, @Query("auth-token") String authToken);

    @POST("./items/add")
    @FormUrlEncoded
    Completable postMoney(@Field("price") double price, @Field("name") String name, @Field("type") String type, @Field("auth-token") String authToken);


}
