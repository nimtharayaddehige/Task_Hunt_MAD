package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mytaskcomplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mytaskcomplete);

        // Handle window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.okImage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle OKimage click
        ImageView okImage = findViewById(R.id.okImage);
        okImage.setOnClickListener(v -> {
            Toast.makeText(this, "Returning to the previous screen", Toast.LENGTH_SHORT).show();
            onBackPressed(); // Simulate back button press
        });
    }

    @Override
    public void onBackPressed() {
        // Handle back button press
        Toast.makeText(this, "Returning to the previous screen", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
