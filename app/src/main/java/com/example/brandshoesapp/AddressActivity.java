package com.example.brandshoesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandshoesapp.adapter.AddressAdapter;
import com.example.brandshoesapp.model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.OnAddressActionListener {
    Button addressBtn, paymentBtn;
    RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private List<AddressModel> addressModels;
    FirebaseFirestore db;
    FirebaseAuth auth;
    String mAddress = "";
    double passedTotalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        passedTotalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        addressBtn = findViewById(R.id.address_Btn);
        paymentBtn = findViewById(R.id.payment_Btn);

        recyclerView = findViewById(R.id.address_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressModels = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, addressModels, this);
        recyclerView.setAdapter(addressAdapter);

        loadAddresses();

        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddress.isEmpty()) {
                    Toast.makeText(AddressActivity.this, "Please select an address", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                intent.putExtra("amount", String.valueOf(passedTotalPrice));
                startActivity(intent);
            }
        });
    }

    private void loadAddresses() {
        addressModels.clear();
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AddressModel addressModel = documentSnapshot.toObject(AddressModel.class);
                        if (addressModel != null) {
                            addressModel.setDocumentId(documentSnapshot.getId()); 
                            addressModels.add(addressModel);
                        }
                    }
                    addressAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onRadioButtonSelected(String address) {
        mAddress = address;
    }

    @Override
    public void onDeleteClicked(String docId) {
        if (docId == null || docId.isEmpty()) return;

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").document(docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddressActivity.this, "Address deleted", Toast.LENGTH_SHORT).show();
                    loadAddresses(); 
                } else {
                    Toast.makeText(AddressActivity.this, "Error deleting address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
