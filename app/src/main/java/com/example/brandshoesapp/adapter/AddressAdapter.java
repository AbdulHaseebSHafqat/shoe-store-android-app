package com.example.brandshoesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.R;
import com.example.brandshoesapp.model.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    List<AddressModel> addressModelList;
    OnAddressActionListener listener;
    private RadioButton selectedRadioBtn;

    public interface OnAddressActionListener {
        void onRadioButtonSelected(String address);
        void onDeleteClicked(String docId);
    }

    public AddressAdapter(Context context, List<AddressModel> addressModelList, OnAddressActionListener listener) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        AddressModel model = addressModelList.get(position);
        holder.addressItem.setText(model.getUserAddress());
        
        holder.button.setChecked(model.isSelected());
        
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressModel addressModel : addressModelList) {
                    addressModel.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);
                
                if (selectedRadioBtn != null) {
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) v;
                selectedRadioBtn.setChecked(true);
                listener.onRadioButtonSelected(model.getUserAddress());
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClicked(model.getDocumentId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView addressItem;
        RadioButton button;
        ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressItem = itemView.findViewById(R.id.address_add);
            button = itemView.findViewById(R.id.select_address);
            deleteBtn = itemView.findViewById(R.id.delete_address);
        }
    }
}
