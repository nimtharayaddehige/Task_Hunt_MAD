package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class enterapp extends AppCompatActivity {

    // Declare buttons
    Button loginButton, signupButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enterapp);


        // Initialize buttons
        loginButton = findViewById(R.id.loginBtn);
        signupButton = findViewById(R.id.signupBtn);

        // Set onClickListener for Login button
        loginButton.setOnClickListener(v -> {
            // Navigate to Login Activity
            Intent loginIntent = new Intent(enterapp.this, loginpage.class);
            startActivity(loginIntent);
        });

        // Set onClickListener for SignUp button
        signupButton.setOnClickListener(v -> {
            // Navigate to SignUp Activity
            Intent signupIntent = new Intent(enterapp.this, signuppage.class);
            startActivity(signupIntent);
        });
    }
}
