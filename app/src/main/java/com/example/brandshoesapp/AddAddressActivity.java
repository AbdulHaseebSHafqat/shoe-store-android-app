package com.example.brandshoesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddAddressActivity extends AppCompatActivity {
    EditText name, address, city, postal, phoneNumber;
    Button addAddress;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.editText_name);
        address = findViewById(R.id.editText_Address);
        city = findViewById(R.id.editText_city);
        phoneNumber = findViewById(R.id.editText_phone);
        postal = findViewById(R.id.editText_postal);
        addAddress = findViewById(R.id.add_address_Btn);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userCity = city.getText().toString().trim();
                String userPostal = postal.getText().toString().trim();
                String userNumber = phoneNumber.getText().toString().trim();

                if (userName.isEmpty() || userAddress.isEmpty() || userCity.isEmpty() || userPostal.isEmpty() || userNumber.isEmpty()) {
                    Toast.makeText(AddAddressActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Format the address nicely
                String final_address = userName + ", " + userAddress + ", " + userCity + "-" + userPostal + ". Ph: " + userNumber;

                HashMap<String, Object> map = new HashMap<>();
                map.put("userAddress", final_address);

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddAddressActivity.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Go back to the previous screen (Address list)
                                } else {
                                    Toast.makeText(AddAddressActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
