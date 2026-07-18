package com.example.brandshoesapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.adapter.DetailsNewProductsAdapter;
import com.example.brandshoesapp.model.DetailsNewProductsModel;

import java.util.ArrayList;
import java.util.List;

public class DetailsProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DetailsNewProductsModel> modelList;
    DetailsNewProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_products);

        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.details_products_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        modelList = new ArrayList<>();
        adapter = new DetailsNewProductsAdapter(this, modelList);
        recyclerView.setAdapter(adapter);

        if (type != null) {
            if (type.equalsIgnoreCase("nike")) {
                modelList.add(new DetailsNewProductsModel(R.drawable.nike2, "Nike Air Force 1", "Classic street style and comfort.", "4.8", "5500"));
                modelList.add(new DetailsNewProductsModel(R.drawable.new12, "Nike Air Zoom Pegasus", "High-performance running gear.", "4.7", "4800"));
            } else if (type.equalsIgnoreCase("adidas")) {
                modelList.add(new DetailsNewProductsModel(R.drawable.adidas1, "Adidas Pureboost", "Responsive cushioning for daily runs.", "4.6", "3800"));
                modelList.add(new DetailsNewProductsModel(R.drawable.new10, "Adidas Forum Low", "Retro vibes with modern durability.", "4.5", "4200"));
            } else if (type.equalsIgnoreCase("nbalance")) {
                modelList.add(new DetailsNewProductsModel(R.drawable.newb3, "New Balance 574", "Iconic silhouette with premium support.", "4.9", "6200"));
                modelList.add(new DetailsNewProductsModel(R.drawable.new14, "New Balance 327", "Heritage meets bold fashion.", "4.7", "7500"));
            } else if (type.equalsIgnoreCase("puma")) {
                modelList.add(new DetailsNewProductsModel(R.drawable.puma2, "Puma RS-X Reinvent", "Bold design and extreme cushioning.", "4.4", "3900"));
                modelList.add(new DetailsNewProductsModel(R.drawable.new16, "Puma Nitro Runner", "Lightweight speed for every mile.", "4.8", "8500"));
            }
            adapter.notifyDataSetChanged();
        }
    }
}
