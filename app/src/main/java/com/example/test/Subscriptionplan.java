package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Subscriptionplan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptionplan);

        // Reference the Subscribe button
        Button subscribeButton = findViewById(R.id.subscribeButton); // Matches the ID in the XML

        // Set an onClick listener for the Subscribe button
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SubscriptionSuccessfully activity
                Intent intent = new Intent(Subscriptionplan.this, subscriptionsuccesfully.class);
                startActivity(intent);
            }
        });
    }
}
