package com.example.brandshoesapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandshoesapp.adapter.MyCartsAdapter;
import com.example.brandshoesapp.database.CartHelper;
import com.example.brandshoesapp.model.MyCartsModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCartsFragment extends Fragment {
    RecyclerView cartsRecycler;
    TextView totalPriceText;
    MyCartsAdapter myCartsAdapter;
    List<MyCartsModel> myCartsModelList;
    CartHelper cartHelper;
    Button buyNowBtn;
    double overallTotalPrice = 0.0;

    public MyCartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        cartHelper = new CartHelper(getActivity());
        cartsRecycler = root.findViewById(R.id.my_carts_recycler);
        totalPriceText = root.findViewById(R.id.my_carts_totalPrice);
        buyNowBtn = root.findViewById(R.id.buy_now_cart_btn);

        cartsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        myCartsModelList = new ArrayList<>();
        myCartsAdapter = new MyCartsAdapter(getActivity(), myCartsModelList, new MyCartsAdapter.CartUpdateListener() {
            @Override
            public void onCartUpdated() {
                loadCartItems();
            }
        });
        cartsRecycler.setAdapter(myCartsAdapter);

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCartsModelList.isEmpty()) {
                    Toast.makeText(getActivity(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), AddressActivity.class);
                    intent.putExtra("totalPrice", overallTotalPrice);
                    startActivity(intent);
                }
            }
        });

        loadCartItems();

        return root;
    }

    private void loadCartItems() {
        myCartsModelList.clear();
        myCartsModelList.addAll(cartHelper.getAllCartItems());
        myCartsAdapter.notifyDataSetChanged();
        calculateTotalPrice(myCartsModelList);
    }

    private void calculateTotalPrice(List<MyCartsModel> list) {
        double grandTotal = 0.0;
        for (MyCartsModel model : list) {
            try {
                if (model.getTotalPrice() != null) {
                    String cleanPrice = model.getTotalPrice().replaceAll("[^\\d.]", "");
                    if (!cleanPrice.isEmpty()) {
                        grandTotal += Double.parseDouble(cleanPrice);
                    }
                }
            } catch (Exception e) {
                // Ignore invalid data
            }
        }

        overallTotalPrice = grandTotal;

        if (grandTotal == 0) {
            totalPriceText.setText("Total Price: Rs 0");
        } else if (grandTotal == (long) grandTotal) {
            totalPriceText.setText("Total Price: Rs " + String.format(Locale.getDefault(), "%d", (long) grandTotal));
        } else {
            totalPriceText.setText("Total Price: Rs " + String.format(Locale.getDefault(), "%.2f", grandTotal));
        }
    }
}
