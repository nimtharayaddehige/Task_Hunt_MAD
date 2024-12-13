package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class subscriptionsuccesfully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subscriptionsuccesfully);

        // Apply window insets for edge-to-edge
<<<<<<< Updated upstream
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
=======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ResetButton), (v, insets) -> {
>>>>>>> Stashed changes
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navigate to student_home after 5 seconds delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(subscriptionsuccesfully.this, studentHome.class);
            startActivity(intent);
            finish(); // Close the current activity
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}
