package com.example.test;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class studentHome extends AppCompatActivity {
    private Button  findButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Initialize Views

        findButton = findViewById(R.id.findButton);

        findButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(studentHome.this, showcatogerylist.class);
            startActivity(intent);
        });
    }
}