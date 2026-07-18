package com.example.brandshoesapp.ui.newproducts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.R;
import com.example.brandshoesapp.adapter.NewProductsAdapter;
import com.example.brandshoesapp.model.NewProductsModel;

import java.util.ArrayList;
import java.util.List;


public class NewProductsFragment extends Fragment {
    RecyclerView recyclerView;
    NewProductsAdapter adapter;
    List<NewProductsModel> modelList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_products, container, false);
        recyclerView = root.findViewById(R.id.new_products_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        modelList=new ArrayList<>();

        modelList.add(new NewProductsModel(R.drawable.nike2, "Nike Air Force", "New Arrival", "nike"));
        modelList.add(new NewProductsModel(R.drawable.adidas2, "Adidas Ultraboost", "New Arrival", "adidas"));
        modelList.add(new NewProductsModel(R.drawable.puma2, "Puma RS-X", "New Arrival", "puma"));
        modelList.add(new NewProductsModel(R.drawable.newb3, "New Balance 574", "New Arrival", "nbalance"));

        adapter = new NewProductsAdapter(getContext(), modelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }

}
