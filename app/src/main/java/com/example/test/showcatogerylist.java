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

public class showcatogerylist extends AppCompatActivity {

    private Button backButton;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcatogerylist);

        // Initialize Views
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(showcatogerylist.this, studentHome.class);
            startActivity(intent);
        });
    }
}