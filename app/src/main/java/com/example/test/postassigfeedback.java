package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class postassigfeedback extends AppCompatActivity {

    private EditText feedbackInput;
    private RatingBar ratingBar;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postassigfeedback);

        // Initialize UI elements
        feedbackInput = findViewById(R.id.feedbackInput);
        ratingBar = findViewById(R.id.starRating);
        submitButton = findViewById(R.id.submitButton);

        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.submitButton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up Submit button click listener
        submitButton.setOnClickListener(v -> handleSubmitFeedback());
    }

    /**
     * Handles the feedback submission logic.
     */
    private void handleSubmitFeedback() {
        String feedbackText = feedbackInput.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (feedbackText.isEmpty() || rating == 0) {
            Toast.makeText(this, "Please provide feedback and a rating.", Toast.LENGTH_SHORT).show();
        } else {
            // Example logic to handle feedback submission
            Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

            // TODO: Replace this with backend integration or local database storage
            sendFeedbackToServer(feedbackText, rating);

            // Clear input fields after submission
            feedbackInput.setText("");
            ratingBar.setRating(0);
        }
    }

    /**
     * Sends the feedback to a server or database (Placeholder function).
     *
     * @param feedbackText The text feedback.
     * @param rating       The star rating.
     */
    private void sendFeedbackToServer(String feedbackText, float rating) {
        // TODO: Implement actual API call or database save logic here
        // Example: Log the feedback (for debugging purposes)
        System.out.println("Feedback Submitted: " + feedbackText + " | Rating: " + rating);
    }
}
