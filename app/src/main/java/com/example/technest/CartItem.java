package com.example.technest;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String name;
    private int quantity;
    private double price;
    private int imageResourceId;

    // Constructor, getters, and setters
    public CartItem(String name, int quantity, double price, int imageResourceId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}

