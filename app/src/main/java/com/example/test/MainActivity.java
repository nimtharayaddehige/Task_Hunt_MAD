package com.example.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome1); // Displaying the welcome1 layout




        // Using Handler with Looper.getMainLooper() to delay the transition
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Create an Intent to start the welcome2 activity
            Intent intent = new Intent(MainActivity.this, welcome1.class);
            startActivity(intent);
            finish(); // Close MainActivity so the user cannot go back to it
        }, 3000); // 3-second delay
    }
}