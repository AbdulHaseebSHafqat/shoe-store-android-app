package com.example.brandshoesapp.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.brandshoesapp.R;
import com.example.brandshoesapp.ViewAllActivity;
import com.example.brandshoesapp.adapter.CategoryAdapter;
import com.example.brandshoesapp.adapter.PopularAdapter;
import com.example.brandshoesapp.adapter.RecommendedAdapter;
import com.example.brandshoesapp.model.CategoryModel;
import com.example.brandshoesapp.model.PopularModel;
import com.example.brandshoesapp.model.RecommendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    RecyclerView popularRecycler, categoryRecycler, recommRecycler;
    FirebaseFirestore db;

    List<PopularModel> popularModels;
    PopularAdapter popularAdapter;

    List<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;

    List<RecommendedModel> recommendedModels;
    RecommendedAdapter recommendedAdapter;

    ImageSlider imageSlider;
    TextView viewAllPopular, viewAllCategory, viewAllRecommended;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog(getActivity());
        linearLayout = root.findViewById(R.id.home_layout);
        imageSlider = root.findViewById(R.id.image_slider);
        
        // Hide layout initially
        linearLayout.setVisibility(View.GONE);

        popularRecycler = root.findViewById(R.id.popular_recycler);
        categoryRecycler = root.findViewById(R.id.category_recycler);
        recommRecycler = root.findViewById(R.id.recommended_recycler);

        viewAllPopular = root.findViewById(R.id.textViewAll);
        viewAllCategory = root.findViewById(R.id.textViewAll1);
        viewAllRecommended = root.findViewById(R.id.textViewAll2);

        db = FirebaseFirestore.getInstance();

        progressDialog.setTitle("Welcome To Our App");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // View All Click Listeners
        View.OnClickListener viewAllListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
            }
        };
        viewAllPopular.setOnClickListener(viewAllListener);
        viewAllCategory.setOnClickListener(viewAllListener);
        viewAllRecommended.setOnClickListener(viewAllListener);

        // Popular items
        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModels = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(), popularModels);
        popularRecycler.setAdapter(popularAdapter);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModels.add(popularModel);
                            }
                            popularAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Error loading popular products", Toast.LENGTH_SHORT).show();
                        }
                        checkAndShowLayout();
                    }
                });

        // Category Explore items
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModels);
        categoryRecycler.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                Map<String, Object> data = document.getData();
                                if (data != null) {
                                    CategoryModel model = new CategoryModel();
                                    model.setName(getString(data, "name", "Name", "title", "Title"));
                                    model.setType(getString(data, "type", "Type", "category", "Category"));
                                    model.setImg_url(getString(data, "img_url", "Img_url", "image", "Image", "url"));
                                    categoryModels.add(model);
                                }
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Error loading categories", Toast.LENGTH_SHORT).show();
                        }
                        checkAndShowLayout();
                    }
                });

        // Recommended items
        recommRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModels = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), recommendedModels);
        recommRecycler.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModels.add(recommendedModel);
                            }
                            recommendedAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Error loading recommended products", Toast.LENGTH_SHORT).show();
                        }
                        checkAndShowLayout();
                    }
                });

        settingImageSlider();

        return root;
    }

    private String getString(Map<String, Object> data, String... keys) {
        for (String key : keys) {
            if (data.containsKey(key) && data.get(key) != null) {
                return String.valueOf(data.get(key));
            }
        }
        return "";
    }

    private void checkAndShowLayout() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (linearLayout != null) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void settingImageSlider() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
}
