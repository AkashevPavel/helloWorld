package com.example.helloworld.remote;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private String userId;

    @SerializedName("auth_token")
    private String auth_token;

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuth_token() {
        return auth_token;
    }
}
