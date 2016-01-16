package com.mohbajal.grocerylist.database.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mohbajal.grocerylist.database.MySQLiteHelper;
import com.mohbajal.grocerylist.database.dao.Item;
import com.mohbajal.grocerylist.database.dao.Store;

import java.util.ArrayList;
import java.util.List;

public class ItemDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ITEM_NAME };

    public ItemDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Item createItem(String item) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ITEM_NAME, item);
        long insertId = database.insert(MySQLiteHelper.TABLE_ITEM, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEM,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Item newItem = cursorToStore(cursor);
        cursor.close();
        return newItem;
    }

    public void deleteStore(Item item) {
        long id = item.getId();
        System.out.println("Item deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ITEM, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /*public void updateStore(Item item) {
        long id = store.getId();
        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_STORE_NAME , store.getStoreName());

        database.update(MySQLiteHelper.TABLE_STORE, args,
                 MySQLiteHelper.COLUMN_ID +"=" + id, null );

    }*/

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEM,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item item = cursorToStore(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return items;
    }

    private Item cursorToStore(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getLong(0));
        item.setItemName(cursor.getString(1));
        return item;
    }
}
