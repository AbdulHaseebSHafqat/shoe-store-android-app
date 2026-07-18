package com.example.brandshoesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.R;
import com.example.brandshoesapp.model.DetailsOffersModel;

import java.util.List;

public class DetailsOffersAdapter extends RecyclerView.Adapter<DetailsOffersAdapter.ViewHolder> {

    Context context;
    List<DetailsOffersModel> list;

    public DetailsOffersAdapter(Context context, List<DetailsOffersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DetailsOffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.details_offers_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsOffersAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.discount.setText(list.get(position).getDiscount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.details_offers_img);
            name = itemView.findViewById(R.id.details_offers_name);
            price = itemView.findViewById(R.id.details_offers_price);
            discount = itemView.findViewById(R.id.details_offers_discount);
        }
    }
}
