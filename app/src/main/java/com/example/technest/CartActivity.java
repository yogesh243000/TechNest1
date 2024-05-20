package com.example.technest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private TextView totalCostTextView;
    private CartManager cartManager;
    private List<CartItem> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        totalCostTextView = findViewById(R.id.totalCostTextView);
        cartManager = CartManager.getInstance(this);

        // Retrieve cart items
        if (cartManager != null) {
            cartItems.addAll(cartManager.loadCartDataFromStorage());
        }

        // Initialize CartAdapter with cartItems and this context
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(cartAdapter);

        // Handle order placement button click
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(v -> placeOrder());

        // Update UI
        updateCartUI(cartItems);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload cart data from persistent storage and update UI
        cartItems.clear();
        if (cartManager != null) {
            cartItems.addAll(cartManager.loadCartDataFromStorage());
        }
        updateCartUI(cartItems);
    }

    public void updateCartUI(List<CartItem> cartItems) {
        double totalCost = 0;
        for (CartItem item : cartItems) {
            totalCost += item.getPrice() * item.getQuantity();
        }
        totalCostTextView.setText(String.format("Total Cost: $%.2f", totalCost));
    }

    private void placeOrder() {
        Bundle orderBundle = new Bundle();
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<Double> productAmounts = new ArrayList<>();

        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                productNames.add(item.getName());
                productAmounts.add(item.getPrice() * item.getQuantity());
            }

            orderBundle.putStringArrayList("products", productNames);
            double[] amountsArray = new double[productAmounts.size()];
            for (int i = 0; i < productAmounts.size(); i++) {
                amountsArray[i] = productAmounts.get(i);
            }
            orderBundle.putDoubleArray("amounts", amountsArray);

            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("orderBundle", orderBundle);
            startActivity(intent);

            // Clear cart after placing order
            cartManager.clearCartData();
            cartItems.clear();
            updateCartUI(cartItems);
        } else {
            Log.d("CartActivity", "cartItems is null or empty");
            Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
