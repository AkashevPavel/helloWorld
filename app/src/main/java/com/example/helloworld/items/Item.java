package com.example.helloworld.items;

public class Item {
    private String title;
    private String value;

    public Item(String title, String value){
        this.title = title;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }
}
