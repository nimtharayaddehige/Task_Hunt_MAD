package com.example.test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postassigfeedback);

        // Initialize UI elements
        feedbackInput = findViewById(R.id.feedbackInput);
        ratingBar = findViewById(R.id.starRating);
        submitButton = findViewById(R.id.submitButton);

        // Initialize database
        AssignmentDatabaseHelper dbHelper = new AssignmentDatabaseHelper(this, "TaskHunt_Database_SQLite", null, 1);
        database = dbHelper.getWritableDatabase();

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
            // Insert feedback into the database
            ContentValues values = new ContentValues();
            values.put("Feedback", feedbackText);
            values.put("Reviews", String.valueOf(rating));
            values.put("Service_Provider_ID", "ExampleProviderID"); // Replace with actual provider ID

            long newRowId = database.insert("Service_Provider_Feedback", null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

                // Clear input fields after submission
                feedbackInput.setText("");
                ratingBar.setRating(0);
            } else {
                Toast.makeText(this, "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }

    /**
     * SQLiteOpenHelper for managing the database.
     */
    private static class AssignmentDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
        private static final int DATABASE_VERSION = 1;

        // SQL command to create the Service_Provider_Feedback table
        private static final String CREATE_FEEDBACK_TABLE =
                "CREATE TABLE Service_Provider_Feedback (" +
                        "SP_Feedback_ID INTEGER PRIMARY KEY, " +
                        "Feedback TEXT, " +
                        "Reviews TEXT, " +
                        "Service_Provider_ID TEXT, " +
                        "FOREIGN KEY (Service_Provider_ID) REFERENCES Service_Provider(Company_Name));";

        public AssignmentDatabaseHelper(android.content.Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_FEEDBACK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Service_Provider_Feedback");
            onCreate(db);
        }
    }
}