package com.example.helloworld.items;

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
}
