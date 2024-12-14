package com.example.test;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.os.Handler;

public class welcome1 extends AppCompatActivity {

    private static final int DELAY_TIME = 5000; // 5 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome1);

        // Call the method to handle the delayed transition
        switchToWelcome2AfterDelay();
    }

    /**
     * Adds a delay before transitioning to the next activity (welcome2).
     */
    private void switchToWelcome2AfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(welcome1.this, welcome2.class);
            startActivity(intent);
            finish(); // Close welcome1 activity
        }, DELAY_TIME);
    }
}
