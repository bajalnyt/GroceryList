package com.mohbajal.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mohbajal.grocerylist.database.dao.Item;
import com.mohbajal.grocerylist.database.model.ItemDataSource;
import com.mohbajal.grocerylist.database.model.StoreDataSource;

/**
 * Created by 908752 on 1/16/16.
 */
public class ItemsListActivity extends AppCompatActivity {

    ArrayAdapter<Item> adapter;

    private ItemDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        Toast.makeText(ItemsListActivity.this, value+ " added successfully!", Toast.LENGTH_SHORT).show();
    }
}
