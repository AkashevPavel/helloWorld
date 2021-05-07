package com.example.helloworld.items;

import com.example.helloworld.remote.MoneyRemoteItem;

public class Item {
    private String title;
    private String value;
    private int position;

    public Item(String title, String value, int position){
        this.title = title;
        this.value = value;
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() { return position; }

    public static Item getInstance(MoneyRemoteItem moneyRemoteItem){
        int position;
        if(moneyRemoteItem.getItemType().equals("expense")) position = 0;
        else position = 1;
        return new Item(moneyRemoteItem.getItemName(), moneyRemoteItem.getItemPrice() + "", position);
    }
}
