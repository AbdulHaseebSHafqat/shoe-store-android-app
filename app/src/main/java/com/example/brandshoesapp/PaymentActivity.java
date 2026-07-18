package com.example.brandshoesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    TextView subTotal, discount, shipping, total;
    Button checkOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        checkOut = findViewById(R.id.pay_btn);

        // Get the passed amount
        String amount = getIntent().getStringExtra("amount");
        if (amount == null || amount.isEmpty()) amount = "0.0";

        try {
            double subtotalVal = Double.parseDouble(amount);
            double discountVal = 0.0; // Zero discount by default
            double shippingVal = 0.0; // Zero shipping or set to your preferred amount (e.g., 200.0)
            
            double totalVal = subtotalVal - discountVal + shippingVal;

            // Display in Rs instead of $
            subTotal.setText("Rs " + String.format(Locale.getDefault(), "%.0f", subtotalVal));
            discount.setText("Rs " + String.format(Locale.getDefault(), "%.0f", discountVal));
            shipping.setText("Rs " + String.format(Locale.getDefault(), "%.0f", shippingVal));
            total.setText("Rs " + String.format(Locale.getDefault(), "%.0f", totalVal));
            
        } catch (NumberFormatException e) {
            subTotal.setText("Rs 0");
            discount.setText("Rs 0");
            shipping.setText("Rs 0");
            total.setText("Rs 0");
        }

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PlaceOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
