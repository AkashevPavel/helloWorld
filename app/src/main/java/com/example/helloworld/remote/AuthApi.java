package com.example.helloworld.remote;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthApi {

    @GET("./auth")
    Single<AuthResponse> makeLogin (@Query("social_user_id") String socialUserId);
}
