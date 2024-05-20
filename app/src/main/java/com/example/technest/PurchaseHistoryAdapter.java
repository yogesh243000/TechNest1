package com.example.technest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.OrderViewHolder> {

    private List<Order> orderHistory;

    public PurchaseHistoryAdapter(List<Order> orderHistory) {
        this.orderHistory = new ArrayList<>(new HashSet<>(orderHistory));
        Collections.sort(this.orderHistory, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderHistory.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderHistory.size();
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = new ArrayList<>(new HashSet<>(orderHistory));
        Collections.sort(this.orderHistory, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderIdTextView;
        private final TextView dateTextView;
        private final TextView totalAmountTextView;
        private final TextView statusTextView;
        private final TextView productTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            totalAmountTextView = itemView.findViewById(R.id.totalAmountTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            productTextView = itemView.findViewById(R.id.productTextView);
        }

        public void bind(Order order) {
            if (order != null) {
                orderIdTextView.setText("Order ID: " + order.getOrderId());
                dateTextView.setText("Date: " + order.getDate());
                totalAmountTextView.setText("Total Amount: " + order.getTotalAmount());
                statusTextView.setText("Status: " + order.getStatus());
                productTextView.setText("Product: " + order.getProduct());
            }
        }
    }
}
