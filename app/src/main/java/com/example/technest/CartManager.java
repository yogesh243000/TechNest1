package com.example.technest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    private static CartManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CartManager is not initialized. Call getInstance(Context) with a valid context first.");
        }
        return instance;
    }

    public void addToCart(CartItem cartItem) {
        List<CartItem> cartItems = loadCartDataFromStorage();
        cartItems.add(cartItem);
        saveCartDataToStorage(cartItems);
    }

    public void saveCartDataToStorage(List<CartItem> cartItems) {
        String cartItemsJson = gson.toJson(cartItems);
        sharedPreferences.edit().putString(CART_ITEMS_KEY, cartItemsJson).apply();
    }

    public List<CartItem> loadCartDataFromStorage() {
        String cartItemsJson = sharedPreferences.getString(CART_ITEMS_KEY, "");
        if (cartItemsJson.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return gson.fromJson(cartItemsJson, type);
    }

    public void clearCartData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
