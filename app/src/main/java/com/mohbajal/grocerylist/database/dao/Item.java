package com.mohbajal.grocerylist.database.dao;

/**
 * Created by 908752 on 1/16/16.
 */
public class Item {

    private long id;
    private String itemName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return itemName;
    }
}
