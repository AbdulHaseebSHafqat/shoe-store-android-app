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
import com.example.brandshoesapp.model.PopularModel;
import com.example.brandshoesapp.model.ViewAllModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private Context context;
    private List<PopularModel> popularModels;

    public PopularAdapter(Context context, List<PopularModel> popularModels) {
        this.context = context;
        this.popularModels = popularModels;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        PopularModel model = popularModels.get(position);
        
        Glide.with(context)
                .load(model.getImg_url())
                .placeholder(R.drawable.nike1)
                .error(R.drawable.nike1)
                .into(holder.popImg);
                
        holder.name.setText(model.getName());
        holder.desc.setText(model.getDescription());
        holder.rating.setText(model.getRating());
        holder.discount.setText(model.getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllModel viewAllModel = new ViewAllModel();
                viewAllModel.setName(model.getName());
                viewAllModel.setDescription(model.getDescription());
                viewAllModel.setRating(model.getRating());
                viewAllModel.setImg_url(model.getImg_url());
                viewAllModel.setType(model.getType());
                
                // Use price if available, otherwise use discount
                if (model.getPrice() != null && !model.getPrice().isEmpty()) {
                    viewAllModel.setPrice(model.getPrice());
                } else {
                    viewAllModel.setPrice(model.getDiscount());
                }

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("details", viewAllModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name, desc, rating, discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg = itemView.findViewById(R.id.popular_item_image);
            name = itemView.findViewById(R.id.popular_title);
            desc = itemView.findViewById(R.id.popular_description);
            rating = itemView.findViewById(R.id.popular_rating);
            discount = itemView.findViewById(R.id.popular_discount);
        }
    }
}
