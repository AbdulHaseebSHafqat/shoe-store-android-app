package com.example.brandshoesapp.ui.offers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.R;
import com.example.brandshoesapp.adapter.OffersAdapter;
import com.example.brandshoesapp.model.OffersModel;

import java.util.ArrayList;
import java.util.List;


public class OffersFragment extends Fragment {
    RecyclerView recyclerView;
    List<OffersModel> modelList;
    OffersAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_offers, container, false);

        recyclerView = root.findViewById(R.id.offers_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        modelList = new ArrayList<>();
        modelList.add(new OffersModel(R.drawable.nike3,"Nike Air Max 270","30% OFF","nike"));
        modelList.add(new OffersModel(R.drawable.newb4,"New Balance Fresh Foam","40% OFF","nbalance"));
        modelList.add(new OffersModel(R.drawable.adidas4,"Adidas Stan Smith","45% OFF","adidas"));
        modelList.add(new OffersModel(R.drawable.puma4,"Puma Suede Classic","55% OFF","puma"));

        adapter = new OffersAdapter(getContext(), modelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return root;
    }

}
