package com.example.technest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1; // Increment version to trigger upgrade

    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + OrderContract.OrderEntry.TABLE_NAME + " (" +
                        OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY," +
                        OrderContract.OrderEntry.COLUMN_NAME_ORDER_ID + " TEXT," +
                        OrderContract.OrderEntry.COLUMN_NAME_DATE + " TEXT," +
                        OrderContract.OrderEntry.COLUMN_NAME_TOTAL_AMOUNT + " REAL," +
                        OrderContract.OrderEntry.COLUMN_NAME_STATUS + " TEXT," +
                        OrderContract.OrderEntry.COLUMN_NAME_PRODUCT_NAME + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) {
            // Create a new table with the updated schema
            String createNewTableSQL = "CREATE TABLE new_table (" +
                    OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY," +
                    OrderContract.OrderEntry.COLUMN_NAME_ORDER_ID + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_NAME_DATE + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_NAME_TOTAL_AMOUNT + " REAL," +
                    OrderContract.OrderEntry.COLUMN_NAME_STATUS + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_NAME_PRODUCT_NAME + " TEXT)";

            String copyDataSQL = "INSERT INTO new_table SELECT " +
                    OrderContract.OrderEntry._ID + ", " +
                    OrderContract.OrderEntry.COLUMN_NAME_ORDER_ID + ", " +
                    OrderContract.OrderEntry.COLUMN_NAME_DATE + ", " +
                    OrderContract.OrderEntry.COLUMN_NAME_TOTAL_AMOUNT + ", " +
                    OrderContract.OrderEntry.COLUMN_NAME_STATUS + ", " +
                    OrderContract.OrderEntry.COLUMN_NAME_PRODUCT_NAME +
                    " FROM " + OrderContract.OrderEntry.TABLE_NAME;

            db.execSQL(createNewTableSQL);
            db.execSQL(copyDataSQL);

            // Drop the old table
            String dropOldTableSQL = "DROP TABLE " + OrderContract.OrderEntry.TABLE_NAME;
            db.execSQL(dropOldTableSQL);

            // Rename the new table to match the original table name
            String renameTableSQL = "ALTER TABLE new_table RENAME TO " + OrderContract.OrderEntry.TABLE_NAME;
            db.execSQL(renameTableSQL);
        }
    }
}