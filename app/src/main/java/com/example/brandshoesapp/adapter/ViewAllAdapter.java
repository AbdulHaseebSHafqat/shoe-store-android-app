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
import com.example.brandshoesapp.model.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    Context context;
    List<ViewAllModel> viewAllModels;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModels) {
        this.context = context;
        this.viewAllModels = viewAllModels;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        ViewAllModel model = viewAllModels.get(position);

        Glide.with(context)
                .load(model.getImg_url())
                .placeholder(R.drawable.nike1)
                .error(R.drawable.nike1)
                .into(holder.viewImage);

        holder.name.setText(model.getName());
        holder.desc.setText(model.getDescription());
        holder.rating.setText(model.getRating());
        holder.price.setText(model.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("details", model);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView viewImage;
        TextView name, desc, rating, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewImage = itemView.findViewById(R.id.viewAll_img);
            name = itemView.findViewById(R.id.viewAll_name);
            desc = itemView.findViewById(R.id.viewAll_description);
            rating = itemView.findViewById(R.id.viewAll_rating);
            price = itemView.findViewById(R.id.view_all_price);
        }
    }
}
