package com.example.technest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    private int quantity = 1; // Initial quantity
    private TextView textViewProductPrice; // Declare as class-level variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Find views
        TextView textViewProductName = findViewById(R.id.textViewProductName);
        TextView textViewProductDescription = findViewById(R.id.textViewProductDescription);
        textViewProductPrice = findViewById(R.id.textViewProductPrice); // Initialize here
        TextView textViewQuantity = findViewById(R.id.textViewQuantity);
        Button buttonDecreaseQuantity = findViewById(R.id.buttonDecreaseQuantity);
        Button buttonIncreaseQuantity = findViewById(R.id.buttonIncreaseQuantity);
        Button buttonAddToCart = findViewById(R.id.buttonAddToCart);
        ImageView imageViewProduct = findViewById(R.id.productImageView);

        // Example product data
        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        String productPrice = intent.getStringExtra("productPrice");
        String productDescription = intent.getStringExtra("productDescription");
        int productImageResourceId = intent.getIntExtra("productImageResourceId", R.drawable.iphone);
        imageViewProduct.setImageResource(productImageResourceId);

        // Retrieve other product details as needed
        ArrayList<CartItem> cartItems = new ArrayList<>();
        CartAdapter adapter = new CartAdapter(cartItems, this);
        textViewProductName.setText(productName);
        textViewProductDescription.setText(productDescription);
        textViewProductPrice.setText("Price: $" + productPrice);

        // Set initial quantity
        textViewQuantity.setText(String.valueOf(quantity));

        buttonIncreaseQuantity.setOnClickListener(view -> {
            quantity++;
            textViewQuantity.setText(String.valueOf(quantity));
            updateTotalPrice(productPrice, quantity);
        });

        buttonDecreaseQuantity.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                textViewQuantity.setText(String.valueOf(quantity));
                updateTotalPrice(productPrice, quantity);
            }
        });


        buttonAddToCart.setOnClickListener(view -> {
            double price = Double.parseDouble(productPrice.replace("$", ""));
            addToCart(productName, quantity, price, productImageResourceId);
            Log.d("ProductDetailActivity", "Added to cart: " + productName + ", Quantity: " + quantity + ", Price: " + price + ", ImageResId: " + productImageResourceId);
            Intent cartIntent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(cartIntent);
        });
    }
        private void updateTotalPrice(String productPrice, int quantity) {
        // Remove the "$" symbol from the product price string and parse it to double
        String cleanedPrice = productPrice.replace("$", "");
        double price = Double.parseDouble(cleanedPrice);

        // Calculate the total price by multiplying the unit price with the quantity
        double totalPrice = price * quantity;

        // Update the TextView to display the total price
        textViewProductPrice.setText("Total Price: $" + totalPrice);
    }

    private void addToCart(String productName, int quantity, double price, int imageResourceId) {
        CartItem cartItem = new CartItem(productName, quantity, price, imageResourceId);
        CartManager.getInstance(this).addToCart(cartItem);
    }

}
