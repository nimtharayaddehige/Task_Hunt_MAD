package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class passwordsuccessful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set the layout for the activity
        setContentView(R.layout.activity_passwordsuccessful);

        // Apply window insets for proper padding of the main view
        View mainView = findViewById(R.id.ResetButton);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (view, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Navigate to studentHome after a 5-second delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(passwordsuccessful.this, studentHome.class);
            startActivity(intent);
            finish(); // Close the current activity
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}
