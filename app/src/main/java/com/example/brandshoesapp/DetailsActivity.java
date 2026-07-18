package com.example.brandshoesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.brandshoesapp.database.CartHelper;
import com.example.brandshoesapp.model.MyCartsModel;
import com.example.brandshoesapp.model.ViewAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    ImageView detailsImage;
    TextView quantity;
    int totalQuantity = 1;
    double totalPrice = 0.0;
    TextView name, rating, description, price;
    Button addToCart, buyNow;
    ImageView addBtn, removeBtn;

    ViewAllModel viewAllModel = null;
    CartHelper cartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        cartHelper = new CartHelper(this);

        final Object object = getIntent().getSerializableExtra("details");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }

        detailsImage = findViewById(R.id.details_img);
        name = findViewById(R.id.details_name);
        rating = findViewById(R.id.details_rating);
        description = findViewById(R.id.details_desc);
        price = findViewById(R.id.details_price);
        addToCart = findViewById(R.id.add_to_cart_Btn);
        buyNow = findViewById(R.id.buy_now_Btn);
        addBtn = findViewById(R.id.add_item);
        removeBtn = findViewById(R.id.remove_item);
        quantity = findViewById(R.id.quantity);

        if (viewAllModel != null) {
            // Priority 1: Load local resource if it exists (for New Arrivals)
            if (viewAllModel.getImageRes() != 0) {
                detailsImage.setImageResource(viewAllModel.getImageRes());
            } 
            // Priority 2: Load from URL (for Popular/All Shoes)
            else if (viewAllModel.getImg_url() != null && !viewAllModel.getImg_url().isEmpty()) {
                Glide.with(getApplicationContext())
                        .load(viewAllModel.getImg_url())
                        .placeholder(R.drawable.nike1)
                        .error(R.drawable.nike1)
                        .into(detailsImage);
            }

            name.setText(viewAllModel.getName());
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            
            updateTotalPrice();
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    updateTotalPrice();
                }
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    updateTotalPrice();
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalPrice <= 0) {
                    Toast.makeText(DetailsActivity.this, "Price is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(DetailsActivity.this, AddressActivity.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });
    }

    private void updateTotalPrice() {
        if (viewAllModel != null && viewAllModel.getPrice() != null && !viewAllModel.getPrice().isEmpty()) {
            String priceString = viewAllModel.getPrice().replaceAll("[^\\d.]", "");
            try {
                if (!priceString.isEmpty()) {
                    double pricePerItem = Double.parseDouble(priceString);
                    totalPrice = pricePerItem * totalQuantity;
                }
            } catch (NumberFormatException e) {
                totalPrice = 0.0;
            }
        }
        
        price.setText("Price: Rs " + String.format(Locale.US, "%.0f", totalPrice));
    }

    private void addedToCart() {
        if (viewAllModel == null || totalPrice <= 0) {
            Toast.makeText(this, "Cannot add item", Toast.LENGTH_SHORT).show();
            return;
        }

        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy", Locale.US);
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a", Locale.US);
        saveCurrentTime = currentTime.format(calendar.getTime());

        MyCartsModel cartModel = new MyCartsModel();
        cartModel.setProductName(viewAllModel.getName());
        cartModel.setProductPrice(viewAllModel.getPrice());
        cartModel.setCurrentDate(saveCurrentDate);
        cartModel.setCurrentTime(saveCurrentTime);
        cartModel.setTotalQuantity(String.valueOf(totalQuantity));
        cartModel.setTotalPrice(String.valueOf(totalPrice));
        cartModel.setImg_url(viewAllModel.getImg_url());
        cartModel.setImageRes(viewAllModel.getImageRes()); // Correctly save local image

        cartHelper.addToCart(cartModel);
        Toast.makeText(this, "Added To Cart", Toast.LENGTH_SHORT).show();
        finish();
    }
}
