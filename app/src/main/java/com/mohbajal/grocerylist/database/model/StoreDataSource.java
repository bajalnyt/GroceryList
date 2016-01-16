package com.mohbajal.grocerylist.database.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mohbajal.grocerylist.database.MySQLiteHelper;
import com.mohbajal.grocerylist.database.dao.Store;

public class StoreDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_STORE_NAME };

    public StoreDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Store createStore(String Store) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_STORE_NAME, Store);
        long insertId = database.insert(MySQLiteHelper.TABLE_STORE, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STORE,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Store newStore = cursorToStore(cursor);
        cursor.close();
        return newStore;
    }

    public void deleteStore(Store Store) {
        long id = Store.getId();
        System.out.println("Store deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_STORE, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Store> getAllStores() {
        List<Store> Stores = new ArrayList<Store>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_STORE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Store Store = cursorToStore(cursor);
            Stores.add(Store);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Stores;
    }

    private Store cursorToStore(Cursor cursor) {
        Store Store = new Store();
        Store.setId(cursor.getLong(0));
        Store.setStoreName(cursor.getString(1));
        return Store;
    }
}
