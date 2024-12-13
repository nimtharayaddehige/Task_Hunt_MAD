package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Terms extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_terms);

        // Adjust padding for ResetButton based on system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ResetButton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Add a 5-second delay before navigating back to the previous form
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Terms.this, signuppage.class);
            startActivity(intent);
            finish();
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}
