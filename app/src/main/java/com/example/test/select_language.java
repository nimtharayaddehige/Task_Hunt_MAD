package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class select_language extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_language);

        // Adjust for system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ResetButton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        RadioButton englishRadioButton = findViewById(R.id.englishRadioButton);
        RadioButton sinhalaRadioButton = findViewById(R.id.sinhalaRadioButton);
        RadioButton tamilRadioButton = findViewById(R.id.tamilRadioButton);
        Button getStartedButton = findViewById(R.id.getStartedButton);

        // Set click listener on Get Started button
        getStartedButton.setOnClickListener(v -> {
            // Ensure one radio button is selected
            if (!englishRadioButton.isChecked() && !sinhalaRadioButton.isChecked() && !tamilRadioButton.isChecked()) {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show delay and transition to next activity
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(select_language.this, enterapp.class);
                startActivity(intent);
                finish(); // Close select_language activity
            }, DELAY_TIME);
        });
    }
}
