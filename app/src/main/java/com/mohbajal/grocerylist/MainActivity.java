package com.mohbajal.grocerylist;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mohbajal.grocerylist.database.dao.Store;
import com.mohbajal.grocerylist.database.model.StoreDataSource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    Button addListItemButton;
    @Bind(R.id.main_listname) EditText storeName;
    @Bind(R.id.main_list_view) ListView storesList;

    List<Store> listItems;
    ArrayAdapter<Store> adapter;

    private StoreDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        datasource = new StoreDataSource(this);
        datasource.open();

        listItems = datasource.getAllStores();

        adapter = new ArrayAdapter<Store>(this, android.R.layout.simple_list_item_1, listItems);

        storesList.setAdapter(adapter);



    }

    @OnItemClick(R.id.main_list_view)
    public void mainListItemClicked(int position){
        Toast.makeText(MainActivity.this, "Position " + position  , Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.main_btn_add)
    public void mainAddButtonClicked(){

        String newItem = storeName.getText().toString();

        if(!validateListItemName(newItem)){
            return;
        }

        storeName.setText(""); //Clear the text

        ArrayAdapter<Store> adapter = (ArrayAdapter<Store>) storesList.getAdapter();
        Store store = null;

        store = datasource.createStore(newItem);
        adapter.add(store);

        adapter.notifyDataSetChanged();

        Toast.makeText(MainActivity.this, "Shopping List "+newItem + " added successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    //Utility Methods [Refactor]
    private boolean validateListItemName(String newItem) {
        if(newItem.trim().length()==0){
            Toast.makeText(MainActivity.this, "Please enter a shopping list name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(listItems.contains(newItem)){
            Toast.makeText(MainActivity.this, "Item already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
