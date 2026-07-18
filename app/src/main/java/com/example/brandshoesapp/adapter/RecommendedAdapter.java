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

import com.bumptech.glide.Glide;
import com.example.brandshoesapp.DetailsActivity;
import com.example.brandshoesapp.R;
import com.example.brandshoesapp.model.RecommendedModel;
import com.example.brandshoesapp.model.ViewAllModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {
    private Context context;
    private List<RecommendedModel> list;

    public RecommendedAdapter(Context context, List<RecommendedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        RecommendedModel model = list.get(position);

        Glide.with(context)
                .load(model.getImg_url())
                .placeholder(R.drawable.nike1)
                .error(R.drawable.nike1)
                .into(holder.recommImage);

        holder.name.setText(model.getName());
        holder.desc.setText(model.getDescription());
        holder.rating.setText(model.getRating());
        
        // Show price below the title
        if (model.getPrice() != null && !model.getPrice().isEmpty()) {
            holder.price.setText("Rs " + model.getPrice());
        } else {
            holder.price.setText("Price N/A");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert RecommendedModel to ViewAllModel to show in DetailsActivity
                ViewAllModel viewAllModel = new ViewAllModel();
                viewAllModel.setName(model.getName());
                viewAllModel.setDescription(model.getDescription());
                viewAllModel.setRating(model.getRating());
                viewAllModel.setImg_url(model.getImg_url());
                
                // Set the price from RecommendedModel
                if (model.getPrice() != null && !model.getPrice().isEmpty()) {
                    viewAllModel.setPrice(model.getPrice());
                } else {
                    viewAllModel.setPrice("0"); // Default if no price found
                }

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
        ImageView recommImage;
        TextView name, desc, rating, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommImage = itemView.findViewById(R.id.recommended_item_image);
            name = itemView.findViewById(R.id.recommended_title);
            desc = itemView.findViewById(R.id.recommended_description);
            rating = itemView.findViewById(R.id.recommended_rating);
            price = itemView.findViewById(R.id.recommended_price);
        }
    }
}
