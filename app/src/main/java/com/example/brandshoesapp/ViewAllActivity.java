package com.example.brandshoesapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.adapter.ViewAllAdapter;
import com.example.brandshoesapp.model.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewAllActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView viewRecycler;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching for shoes...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String type = getIntent().getStringExtra("type");
        
        viewRecycler = findViewById(R.id.viewAll_Recycler);
        viewRecycler.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelList);
        viewRecycler.setAdapter(viewAllAdapter);

        // Fetch everything from 'AllShoes' and filter locally for 100% reliability
        db.collection("AllShoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dismissDialog();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Map<String, Object> data = document.getData();
                        if (data == null) continue;

                        ViewAllModel model = new ViewAllModel();
                        
                        // Robust field extraction
                        model.setName(getString(data, "name", "title", "productName", "Name"));
                        model.setDescription(getString(data, "description", "desc", "detail", "Description"));
                        model.setRating(getString(data, "rating", "rate", "stars", "Rating"));
                        model.setPrice(getString(data, "price", "productPrice", "discount", "Price", "value"));
                        model.setImg_url(getString(data, "img_url", "image", "imageUrl", "url", "img", "Img_url"));
                        model.setType(getString(data, "type", "category", "brand", "Type", "Category"));

                        // Universal Search Logic
                        if (type == null || type.trim().isEmpty() || type.equalsIgnoreCase("all")) {
                            viewAllModelList.add(model);
                        } else {
                            String query = type.toLowerCase().trim();
                            boolean isMatch = false;
                            
                            // Check every field in the document for the category name
                            for (Object value : data.values()) {
                                if (value != null) {
                                    String valStr = String.valueOf(value).toLowerCase();
                                    if (valStr.contains(query) || query.contains(valStr)) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                            }
                            
                            // Check document ID as well
                            if (!isMatch && document.getId().toLowerCase().contains(query)) {
                                isMatch = true;
                            }

                            if (isMatch) {
                                viewAllModelList.add(model);
                            }
                        }
                    }
                    viewAllAdapter.notifyDataSetChanged();
                    
                    if (viewAllModelList.isEmpty()) {
                        String categoryName = (type != null && !type.isEmpty()) ? type : "this category";
                        Toast.makeText(ViewAllActivity.this, "No products found for: " + categoryName, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ViewAllActivity.this, "Database connection error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getString(Map<String, Object> data, String... keys) {
        for (String key : keys) {
            if (data.containsKey(key) && data.get(key) != null) {
                return String.valueOf(data.get(key));
            }
        }
        return "";
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
