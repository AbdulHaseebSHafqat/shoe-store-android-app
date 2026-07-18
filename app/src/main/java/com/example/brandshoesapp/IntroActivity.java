package com.example.brandshoesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends AppCompatActivity {
    Button login, signup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        auth = FirebaseAuth.getInstance();

        login = findViewById(R.id.loginBtn_Intro);
        signup = findViewById(R.id.signupBtn_Intro);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            Toast.makeText(this, "Please wait, you're already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, SignUpActivity.class));
            }
        });
    }
}
