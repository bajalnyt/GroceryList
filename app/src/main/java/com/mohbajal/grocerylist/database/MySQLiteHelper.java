package com.mohbajal.grocerylist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_STORE = "store";
    public static final String TABLE_ITEM = "item";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STORE_NAME = "store_name";

    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_STORE_ID = "store_id";

    private static final String DATABASE_NAME = "store.db";
    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATE_STORE_TABLE = "create table "
            + TABLE_STORE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_STORE_NAME
            + " text not null);";

    private static final String DATABASE_CREATE_ITEM_TABLE = "create table "
            + TABLE_ITEM + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_STORE_ID
            + " integer , "+ COLUMN_ITEM_NAME
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_STORE_TABLE);
        database.execSQL(DATABASE_CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db);
    }

}