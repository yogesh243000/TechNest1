package com.example.technest;

import java.util.Objects;

public class Order {
    private String orderId;
    private String date;
    private String status;
    private String product;
    private double totalAmount;

    public Order(String orderId, String date, double totalAmount, String status, String product) {
        this.orderId = orderId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
        this.product = product;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getProduct() {
        return product;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId) &&
                date.equals(order.date) &&
                status.equals(order.status) &&
                product.equals(order.product) &&
                Double.compare(order.totalAmount, totalAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, date, status, product, totalAmount);
    }
}
