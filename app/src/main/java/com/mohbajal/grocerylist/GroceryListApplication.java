package com.mohbajal.grocerylist;

import android.app.Application;

import com.mohbajal.grocerylist.model.Store;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

/**
 * Created by 908752 on 1/10/16.
 */
public class GroceryListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
        ParseObject.registerSubclass(Store.class);
        //ParseACL.setDefaultACL(defaultACL, true);
    }
}
