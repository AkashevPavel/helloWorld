package com.example.helloworld.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoneyResponse {

    private String status;

    @SerializedName("data")
    private List<MoneyRemoteItem> moneyItemList;

    public List<MoneyRemoteItem> getMoneyItemList() {
        return moneyItemList;
    }

    public String getStatus() {
        return status;
    }
}
