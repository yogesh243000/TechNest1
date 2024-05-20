package com.example.technest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private OrderDbHelper dbHelper;

    public OrderDao(Context context) {
        dbHelper = new OrderDbHelper(context);
    }

    public void insertOrder(String date, double totalAmount, String status, String productName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String orderId = generateOrderId(); // Generate the order ID
        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME_ORDER_ID, orderId);
        values.put(OrderContract.OrderEntry.COLUMN_NAME_DATE, date);
        values.put(OrderContract.OrderEntry.COLUMN_NAME_TOTAL_AMOUNT, totalAmount);
        values.put(OrderContract.OrderEntry.COLUMN_NAME_STATUS, status);
        values.put(OrderContract.OrderEntry.COLUMN_NAME_PRODUCT_NAME, productName);

        db.insert(OrderContract.OrderEntry.TABLE_NAME, null, values);
        db.close();
    }

    public List<Order> getAllOrders() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Order> orderList = new ArrayList<>();
        Log.d("OrderDao", "Fetching all orders from the database.");

        Cursor cursor = db.query(
                OrderContract.OrderEntry.TABLE_NAME,
                null, // Columns - null means all columns
                null, // Selection
                null, // Selection arguments
                null, // Group by
                null, // Having
                OrderContract.OrderEntry.COLUMN_NAME_DATE + " DESC"  // Order by date descending
        );

        while (cursor.moveToNext()) {
            String orderId = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_NAME_ORDER_ID));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_NAME_DATE));
            double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_NAME_TOTAL_AMOUNT));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_NAME_STATUS));
            String product = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_NAME_PRODUCT_NAME));

            // Only add orders with non-empty product names
            if (!product.isEmpty()) {
                Log.d("OrderDao", "Order: " + orderId + ", Date: " + date + ", Total Amount: " + totalAmount + ", Status: " + status + ", Product: " + product);
                orderList.add(new Order(orderId, date, totalAmount, status, product));
            }
        }
        cursor.close();
        db.close();
        return orderList;
    }

    public void deleteAllOrders() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(OrderContract.OrderEntry.TABLE_NAME, null, null);
        db.close();
    }

    private String generateOrderId() {
        // Get current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a random number between 1000 and 9999
        int randomNumber = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;

        // Concatenate timestamp and random number to create the order ID
        return "ORD" + timestamp + randomNumber;
    }
}
