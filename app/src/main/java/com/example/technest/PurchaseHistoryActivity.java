package com.example.technest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class PurchaseHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPurchaseHistory;
    private PurchaseHistoryAdapter purchaseHistoryAdapter;
    private OrderDao orderDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_history_activity);

        recyclerViewPurchaseHistory = findViewById(R.id.recyclerViewPurchaseHistory);
        recyclerViewPurchaseHistory.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the OrderDao
        orderDao = new OrderDao(this);

        // Retrieve the user's purchase history data from the database
        List<Order> orderHistory = orderDao.getAllOrders();

        // Set up the RecyclerView with the order history
        if (orderHistory != null && !orderHistory.isEmpty()) {
            purchaseHistoryAdapter = new PurchaseHistoryAdapter(orderHistory);
            recyclerViewPurchaseHistory.setAdapter(purchaseHistoryAdapter);
        } else {
            StyleableToast.makeText(PurchaseHistoryActivity.this, "No purchase history found", R.style.mytoast).show();
        }


        // Find the delete button and set an OnClickListener on it
        Button buttonDeleteAllHistory = findViewById(R.id.buttonDeleteAllHistory);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.button_color);
        buttonDeleteAllHistory.setBackground(drawable);
        buttonDeleteAllHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all orders from the database
                orderDao.deleteAllOrders();

                // Notify the user that the history has been deleted
                StyleableToast.makeText(PurchaseHistoryActivity.this, "Purchase history deleted", R.style.mytoast).show();
                // Refresh the RecyclerView to show an empty list
                List<Order> emptyList = orderDao.getAllOrders();
                purchaseHistoryAdapter.setOrderHistory(emptyList);
            }
        });
    }
}
