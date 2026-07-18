package com.example.brandshoesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.DetailsActivity;
import com.example.brandshoesapp.R;
import com.example.brandshoesapp.model.DetailsNewProductsModel;
import com.example.brandshoesapp.model.ViewAllModel;

import java.util.List;

public class DetailsNewProductsAdapter extends RecyclerView.Adapter<DetailsNewProductsAdapter.ViewHolder> {
    Context context;
    List<DetailsNewProductsModel> list;

    public DetailsNewProductsAdapter(Context context, List<DetailsNewProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DetailsNewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.details_new_products_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsNewProductsAdapter.ViewHolder holder, int position) {
        DetailsNewProductsModel model = list.get(position);
        holder.imageView.setImageResource(model.getImage());
        holder.textView.setText(model.getName());
        holder.price.setText("Rs " + model.getPrice());
        holder.rating.setText(model.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllModel viewAllModel = new ViewAllModel();
                viewAllModel.setName(model.getName());
                viewAllModel.setDescription(model.getDescription());
                viewAllModel.setRating(model.getRating());
                viewAllModel.setPrice(model.getPrice());
                viewAllModel.setImg_url(""); 
                viewAllModel.setImageRes(model.getImage()); // Pass the local resource ID

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("details", viewAllModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, price, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.details_new_product_img);
            textView = itemView.findViewById(R.id.details_Nproducts_name);
            price = itemView.findViewById(R.id.details_new_price);
            rating = itemView.findViewById(R.id.details_new_rating);
        }
    }
}
