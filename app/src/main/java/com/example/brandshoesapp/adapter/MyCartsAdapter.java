package com.example.brandshoesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.brandshoesapp.R;
import com.example.brandshoesapp.database.CartHelper;
import com.example.brandshoesapp.model.MyCartsModel;

import java.util.List;
import java.util.Locale;

public class MyCartsAdapter extends RecyclerView.Adapter<MyCartsAdapter.ViewHolder> {

    Context context;
    List<MyCartsModel> myCartsModels;
    CartHelper cartHelper;
    CartUpdateListener listener;

    public interface CartUpdateListener {
        void onCartUpdated();
    }

    public MyCartsAdapter(Context context, List<MyCartsModel> myCartsModels, CartUpdateListener listener) {
        this.context = context;
        this.myCartsModels = myCartsModels;
        this.listener = listener;
        this.cartHelper = new CartHelper(context);
    }

    @NonNull
    @Override
    public MyCartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_carts_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartsAdapter.ViewHolder holder, int position) {
        MyCartsModel model = myCartsModels.get(position);
        holder.name.setText(model.getProductName());
        holder.price.setText("Price: Rs " + model.getProductPrice());
        holder.quantity.setText("Qty: " + model.getTotalQuantity());
        
        // Format the subtotal for this item
        try {
            double total = Double.parseDouble(model.getTotalPrice());
            holder.totalPrice.setText("Rs " + String.format(Locale.getDefault(), "%.0f", total));
        } catch (Exception e) {
            holder.totalPrice.setText("Rs " + model.getTotalPrice());
        }

        // Load item image (handles both local resource and URL)
        if (model.getImageRes() != 0) {
            holder.productImg.setImageResource(model.getImageRes());
        } else {
            Glide.with(context)
                    .load(model.getImg_url())
                    .placeholder(R.drawable.nike1)
                    .error(R.drawable.nike1)
                    .into(holder.productImg);
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartHelper.deleteItem(model.getDocumentId());
                Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onCartUpdated();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCartsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity, totalPrice;
        ImageView deleteBtn, productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.myCart_product_name);
            price = itemView.findViewById(R.id.myCart_product_price);
            quantity = itemView.findViewById(R.id.myCart_total_Quantity);
            totalPrice = itemView.findViewById(R.id.myCartItem_total_Price);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            productImg = itemView.findViewById(R.id.cart_item_img);
        }
    }
}
