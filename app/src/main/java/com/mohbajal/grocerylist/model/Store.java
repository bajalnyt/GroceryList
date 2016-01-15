package com.mohbajal.grocerylist.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by 908752 on 1/10/16.
 */
@ParseClassName("Photo")
public class Store extends ParseObject {

    public Store() {
    }

    public String getStoreName() {return getString("StoreName");}

    public void setStoreName(String storeName) {
        put("StoreName" ,storeName);
    }

    public static ParseQuery<Store> getQuery() {
        return ParseQuery.getQuery(Store.class);
    }

}
