package com.example.brandshoesapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.adapter.DetailsOffersAdapter;
import com.example.brandshoesapp.model.DetailsOffersModel;

import java.util.ArrayList;
import java.util.List;

public class OffersDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DetailsOffersModel> modelList;
    DetailsOffersAdapter adapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String type= getIntent().getStringExtra("type");

        setContentView(R.layout.activity_offers_details);

        recyclerView = findViewById(R.id.detail_offers_recycler);
        imageView = findViewById(R.id.detail_collapse_img);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();
        adapter = new DetailsOffersAdapter(this, modelList);
        recyclerView.setAdapter(adapter);

        if (type!=null && type.equalsIgnoreCase("nike")){
            imageView.setImageResource(R.drawable.nike3);
            modelList.add(new DetailsOffersModel(R.drawable.nike1,"Nike Air Max 270","Rs 9,499","Discount 30% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.nike2,"Nike Air Force 1","Rs 9,799","Discount 30% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.nike4,"Nike React Infinity","Rs 10,299","Discount 30% OFF"));
            adapter.notifyDataSetChanged();
        }

        if (type!=null && type.equalsIgnoreCase("nbalance")){
            imageView.setImageResource(R.drawable.newb4);
            modelList.add(new DetailsOffersModel(R.drawable.newb1,"New Balance 574","Rs 7,999","Discount 40% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.newb2,"New Balance Fresh Foam","Rs 8,499","Discount 40% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.newb3,"New Balance 990v5","Rs 9,199","Discount 40% OFF"));
            adapter.notifyDataSetChanged();
        }

        if (type!=null && type.equalsIgnoreCase("adidas")){
            imageView.setImageResource(R.drawable.adidas4);
            modelList.add(new DetailsOffersModel(R.drawable.adidas1,"Adidas Ultraboost 22","Rs 8,999","Discount 45% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.adidas2,"Adidas Stan Smith","Rs 6,499","Discount 45% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.adidas3,"Adidas NMD R1","Rs 9,299","Discount 45% OFF"));
            adapter.notifyDataSetChanged();
        }

        if (type!=null && type.equalsIgnoreCase("puma")){
            imageView.setImageResource(R.drawable.puma4);
            modelList.add(new DetailsOffersModel(R.drawable.puma1,"Puma Suede Classic","Rs 5,499","Discount 55% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.puma2,"Puma RS-X Toys","Rs 6,999","Discount 55% OFF"));
            modelList.add(new DetailsOffersModel(R.drawable.puma3,"Puma Cali Sport","Rs 7,499","Discount 55% OFF"));
            adapter.notifyDataSetChanged();
        }

    }
}