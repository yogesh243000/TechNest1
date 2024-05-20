package com.example.technest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    private CartManager cartManager;

    public CartAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
        this.cartManager = CartManager.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.textViewProductName.setText(cartItem.getName());
        holder.textViewProductQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.textViewProductPrice.setText(String.format("$%.2f", cartItem.getPrice() * cartItem.getQuantity())); // Multiply price by quantity
        holder.imageViewProduct.setImageResource(cartItem.getImageResourceId());

        // Handle increase quantity button click
        holder.buttonIncreaseQuantity.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartManager.saveCartDataToStorage(cartItems);
            notifyItemChanged(position);
            // Update total cost in CartActivity
            if (context instanceof CartActivity) {
                ((CartActivity) context).updateCartUI(cartItems);
            }
        });

        // Handle decrease quantity button click
        holder.buttonDecreaseQuantity.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartManager.saveCartDataToStorage(cartItems);
                notifyItemChanged(position);
                // Update total cost in CartActivity
                if (context instanceof CartActivity) {
                    ((CartActivity) context).updateCartUI(cartItems);
                }
            }
        });

        // Handle remove button click
        holder.buttonRemove.setOnClickListener(v -> {
            cartItems.remove(position);
            cartManager.saveCartDataToStorage(cartItems); // Update the storage
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            // Update total cost in CartActivity
            if (context instanceof CartActivity) {
                ((CartActivity) context).updateCartUI(cartItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewProductName;
        public TextView textViewProductQuantity;
        public TextView textViewProductPrice;
        public ImageView imageViewProduct;
        public Button buttonIncreaseQuantity;
        public Button buttonDecreaseQuantity;
        public Button buttonRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            buttonIncreaseQuantity = itemView.findViewById(R.id.buttonIncreaseQuantity);
            buttonDecreaseQuantity = itemView.findViewById(R.id.buttonDecreaseQuantity);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}
