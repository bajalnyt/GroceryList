package com.mohbajal.grocerylist.database.dao;

/**
 * Created by 908752 on 1/16/16.
 */
public class Store {

    private long id;
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return storeName;
    }
}
