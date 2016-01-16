package com.mohbajal.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mohbajal.grocerylist.database.dao.Item;
import com.mohbajal.grocerylist.database.dao.Store;
import com.mohbajal.grocerylist.database.model.ItemDataSource;
import com.mohbajal.grocerylist.database.model.StoreDataSource;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 908752 on 1/16/16.
 */
public class ItemsListActivity extends AppCompatActivity {
    
    @Bind(R.id.items_item_name) EditText itemName;
    
    ArrayAdapter<Item> adapter;
    List<Item> listItems;

    private ItemDataSource datasource;
    @Bind(R.id.item_list_view) ListView itemsList;

    long storeId;

    String[] storeContextMenuItems = new String[] {"Delete", "Rename"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_items_list);

        ButterKnife.bind(this);

        datasource = new ItemDataSource(this);
        datasource.open();

        Intent intent = getIntent();
        storeId = intent.getLongExtra("key", 0);

        listItems = datasource.getAllItemsForStore(storeId); //TODO

        adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, listItems);

        itemsList.setAdapter(adapter);

        registerForContextMenu(itemsList);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.main_list_view) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(listItems.get(info.position).getItemName());

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
        String listItemName = listItems.get(info.position).getItemName();

        Item item1 = (Item) itemsList.getAdapter().getItem(info.position);

        if("Delete".equalsIgnoreCase(menuItemName)) {
            datasource.deleteItem(item1);

            adapter.remove(item1);
            Toast.makeText(ItemsListActivity.this, "listItemName is deleted", Toast.LENGTH_SHORT).show();
        } else if("Rename".equalsIgnoreCase(menuItemName)){

            datasource.updateItem(item1);
            adapter.notifyDataSetChanged();
            Toast.makeText(ItemsListActivity.this, "listItemName is renamed", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @OnClick(R.id.items_btn_add)
    public void onItemAddButtonClicked(){

        String newItem = itemName.getText().toString();

        if(!validateListItemName(newItem)){
            return;
        }

        itemName.setText(""); //Clear the text

        ArrayAdapter<Item> adapter = (ArrayAdapter<Item>) itemsList.getAdapter();
        Item Item = null;

        Item = datasource.createItem(newItem, storeId);
        adapter.add(Item);

        adapter.notifyDataSetChanged();

        Toast.makeText(ItemsListActivity.this, "Item "+newItem + " added successfully!", Toast.LENGTH_SHORT).show();
    }


    //Utility Methods [Refactor]
    private boolean validateListItemName(String newItem) {
        if(newItem.trim().length()==0){
            Toast.makeText(ItemsListActivity.this, "Please enter a shopping list name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(listItems.contains(newItem)){
            Toast.makeText(ItemsListActivity.this, "Item already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
