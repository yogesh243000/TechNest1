package com.example.technest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CheckoutActivity extends AppCompatActivity {

    private EditText fullNameEditText, addressEditText, phoneEditText, emailEditText;
    private Button placeOrderButton;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize EditText fields and Button
        fullNameEditText = findViewById(R.id.fullNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        // Retrieve the total amount from the intent extras
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0);
        Bundle orderBundle = getIntent().getBundleExtra("orderBundle");

        if (orderBundle != null) {
            ArrayList<String> products = orderBundle.getStringArrayList("products");
            double[] amounts = orderBundle.getDoubleArray("amounts");

            OrderDao orderDao = new OrderDao(this);
            Set<Order> uniqueOrders = new HashSet<>();

            for (int i = 0; i < products.size(); i++) {
                String product = products.get(i);
                double amount = amounts[i];
                String orderId = "ORDER_ID";
                String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
                String status = "STATUS";

                Order order = new Order(orderId, date, amount, status, product);
                if (!uniqueOrders.contains(order)) {
                    orderDao.insertOrder(date, amount, status, product);
                    uniqueOrders.add(order);
                }
            }
        }

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                if (fullName.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(CheckoutActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String orderId = "ORD" + System.currentTimeMillis();
                    String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
                    String status = "Pending";

                    OrderDao orderDao = new OrderDao(CheckoutActivity.this);
                    orderDao.insertOrder(date, totalAmount, status, "");

                    processOrder(fullName, address, phone, email);

                    Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("date", date);
                    intent.putExtra("totalAmount", totalAmount);
                    intent.putExtra("status", status);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void processOrder(String fullName, String address, String phone, String email) {
        String message = "Order placed! Details: \n" +
                "Full Name: " + fullName + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
