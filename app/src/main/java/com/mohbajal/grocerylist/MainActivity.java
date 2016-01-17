package com.mohbajal.grocerylist;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

    @Bind(R.id.main_listname)
    AutoCompleteTextView storeName;
    @Bind(R.id.main_list_view) ListView storesList;

    List<Store> listItems;
    ArrayAdapter<Store> adapter;

    private StoreDataSource datasource;

    String[] storeContextMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        datasource = new StoreDataSource(this);
        datasource.open();

        listItems = datasource.getAllStores();

        adapter = new ArrayAdapter<Store>(this, android.R.layout.simple_list_item_1, listItems);

        storesList.setAdapter(adapter);

        //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        //storeName.setAdapter(adapter);


        registerForContextMenu(storesList);
        storeContextMenuItems = getResources().getStringArray(R.array.contextmenuoptions);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.main_list_view) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(listItems.get(info.position).getStoreName());


            for (int i = 0; i< storeContextMenuItems.length; i++) {
                menu.add(Menu.NONE, i, i, storeContextMenuItems[i]);
            }
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        String menuItemName = storeContextMenuItems[menuItemIndex];
        String listItemName = listItems.get(info.position).getStoreName();

        Store store = (Store) storesList.getAdapter().getItem(info.position);

        if("Delete".equalsIgnoreCase(menuItemName)) {
            datasource.deleteStore(store);

            adapter.remove(store);
            //Toast.makeText(MainActivity.this, "listItemName is deleted", Toast.LENGTH_SHORT).show();
        } else if("Rename".equalsIgnoreCase(menuItemName)){
            store.setStoreName("Updated");
            datasource.updateStore(store);

            adapter.notifyDataSetChanged();
            //Toast.makeText(MainActivity.this, "listItemName is renamed", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @OnItemClick(R.id.main_list_view)
    public void mainListItemClicked(int position){
        Store store = (Store) storesList.getAdapter().getItem(position);

        Intent myIntent = new Intent(this, ItemsListActivity.class);
        myIntent.putExtra("key", store.getId()); //Optional parameters
        this.startActivity(myIntent);

    }

    @OnClick(R.id.main_btn_add)
    public void mainAddButtonClicked(){

        String newStore = storeName.getText().toString();

        if(!validateListItemName(newStore)){
            return;
        }

        storeName.setText(""); //Clear the text

        ArrayAdapter<Store> adapter = (ArrayAdapter<Store>) storesList.getAdapter();
        Store store = null;

        store = datasource.createStore(newStore);
        adapter.add(store);

        adapter.notifyDataSetChanged();

        //Toast.makeText(MainActivity.this, "Shopping List "+newStore + " added successfully!", Toast.LENGTH_SHORT).show();

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
