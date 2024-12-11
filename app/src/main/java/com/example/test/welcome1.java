package com.example.test;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.os.Handler;


public class welcome1 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome1);
        new Handler().postDelayed(() ->
        {
            Intent intent = new Intent(welcome1.this, welcome2.class);
            startActivity(intent);
            finish(); // Close MainActivity
        }, 3000);
    }
}