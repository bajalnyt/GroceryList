package com.mohbajal.grocerylist;

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

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    Button addListItemButton;
    @Bind(R.id.main_listname) EditText storeName;
    @Bind(R.id.main_list_view) ListView storesList;

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ParseQueryAdapter<ParseObject> mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);


        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "Store");
        mainAdapter.setTextKey("StoreName");
        storesList.setAdapter(mainAdapter);
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

        listItems.add(newItem);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Shopping List "+newItem + " added successfully!", Toast.LENGTH_SHORT).show();
        storeName.setText(""); //Clear the text

        //Persist to Remote Database
       ParseObject store = new ParseObject("Store");
       store.put("StoreName", newItem);

       store.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Success
                    Toast.makeText(MainActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                } else {
                    e.printStackTrace();
                }
            }
        });


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
