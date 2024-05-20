package com.example.technest;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;            // Name of the product
    private String price;           // Price of the product
    private String description;     // Description of the product
    private int imageResourceId;    // Resource ID of the product image

    // Constructor to initialize the product
    public Product(String name, String price, String description, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Getter methods to retrieve product properties
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    // Static method to provide sample product data
    public static List<Product> getSampleProducts() {
        List<Product> productList = new ArrayList<>();
        // Add sample product data
        productList.add(new Product("Product 1", "$10.00", "Description of Product 1", R.drawable.ic_launcher_background));
        productList.add(new Product("Product 2", "$20.00", "Description of Product 2", R.drawable.ic_launcher_background));
        // Add more sample products as needed
        return productList;
    }
}
