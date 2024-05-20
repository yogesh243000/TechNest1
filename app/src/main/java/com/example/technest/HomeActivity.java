package com.example.technest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ImageView imageView;

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(getProductList());
        recyclerView.setAdapter(productAdapter);
        imageView = findViewById(R.id.buttonCart);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_purchase_history) {
            // Navigate to PurchaseHistoryActivity
            startActivity(new Intent(HomeActivity.this, PurchaseHistoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        // Add sample products
        productList.add(new Product("Iphone 15 Pro Max", "2144.00", "Premium design: Sleek glass front and back, stainless steel frame.\n" +
                "Stunning OLED display: Large screen with vibrant colors and ProMotion technology.\n" +
                "Powerful performance: Latest A-series chip for seamless multitasking and gaming.\n" +
                "Advanced camera system: Multiple lenses with Night mode, Deep Fusion, and Smart HDR.\n" +
                "5G connectivity: Faster download/upload speeds and Wi-Fi 6E support.\n" +
                "Impressive battery life: Large capacity battery with efficient power management.\n" +
                "Enhanced security: Face ID for secure authentication and advanced privacy features.\n" +
                "Latest iOS: Access to a wide range of apps and regular software updates.", R.drawable.iphone));
        productList.add(new Product(" Samsung Galaxy S24 Ultra", "2149.00", "Premium design: Sleek glass front and back, aluminum frame with a modern aesthetic.\n" +
                "Dynamic display: Large, high-resolution AMOLED screen with a high refresh rate for smooth visuals.\n" +
                "Powerful performance: Cutting-edge processor and ample RAM for seamless multitasking and gaming.\n" +
                "Versatile camera system: Multiple high-resolution lenses with advanced features like optical zoom, night mode, and AI enhancements.\n" +
                "5G connectivity: Faster network speeds for streaming, gaming, and downloading.\n" +
                "Long-lasting battery: Large capacity battery with fast charging and wireless charging support.\n" +
                "Enhanced security: Built-in fingerprint sensor, facial recognition, and other security features for protection.\n" +
                "Latest software: Runs on the latest version of Android with regular updates and access to a wide range of apps and services.", R.drawable.samsung));
        productList.add(new Product("OnePlus 8 Pro", "1999.00", "Premium design: Sleek glass front and back, stainless steel frame.\n" +
                "Stunning OLED display: Large screen with vibrant colors and ProMotion technology.\n" +
                "Powerful performance: Latest A-series chip for seamless multitasking and gaming.\n", R.drawable.oneplus));
        // Add more products as needed
        return productList;
    }

    // ViewHolder for Product RecyclerView
    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        ImageView productImageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
        }
    }


    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
        private List<Product> productList;

        public ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }


            @Override
            public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
                Product product = productList.get(position);
                holder.productNameTextView.setText(product.getName());
                holder.productPriceTextView.setText(product.getPrice());
                holder.productImageView.setImageResource(product.getImageResourceId());

                // Set click listener for the product item
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create a new Intent to navigate to the detail page
                        Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                        // Pass any data needed to the detail page using intent extras
                        intent.putExtra("productName", product.getName());
                        intent.putExtra("productPrice", product.getPrice());
                        intent.putExtra("productDescription", product.getDescription());
                        intent.putExtra("productImageResourceId", product.getImageResourceId());
                        // Example: Passing product ID
                        // Start the activity
                        v.getContext().startActivity(intent);

                        // Show a toast message to indicate navigation
                        Toast.makeText(v.getContext(), "Navigating to detail page...", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        @Override
        public int getItemCount() {
            return productList.size();
        }
    }
}