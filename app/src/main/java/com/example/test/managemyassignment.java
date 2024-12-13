package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class managemyassignment extends AppCompatActivity {

    private Button addTaskButton, saveDraftButton;
    private SQLiteDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_managemyassignment);

        // Handle edge-to-edge insets
<<<<<<< Updated upstream
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
=======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ResetButton), (v, insets) -> {
>>>>>>> Stashed changes
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        AssignmentDatabaseHelper dbHelper = new AssignmentDatabaseHelper(this, "TaskHunt_Database_SQLite", null, 1);
        database = dbHelper.getWritableDatabase();

        // Initialize buttons
        addTaskButton = findViewById(R.id.addTaskButton);
<<<<<<< Updated upstream
        saveDraftButton = findViewById(R.id.draftButton);
=======
        saveDraftButton = findViewById(R.id.getStartedButton);
>>>>>>> Stashed changes

        // Set up button click listeners
        addTaskButton.setOnClickListener(v -> addAssignment());
        saveDraftButton.setOnClickListener(v -> saveDraft());
    }

    private void addAssignment() {
        ContentValues values = new ContentValues();
        values.put("Title", "Assignment Title");
        values.put("Description", "Assignment Description");
        values.put("Time", "12:00:00");
        values.put("Due_Date", "2024-12-15");
        values.put("Payment_Offer", 150.00);
        values.put("Post_Status", "Posted");
        values.put("Attach_File", "path/to/file");
        values.put("Complete_Status", "Pending");
        values.put("User_ID", 1); // Replace with actual User ID

        long newRowId = database.insert("Assignment", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Assignment Added Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Add Assignment!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDraft() {
        ContentValues values = new ContentValues();
        values.put("Title", "Draft Title");
        values.put("Description", "Draft Description");
        values.put("Time", "");
        values.put("Due_Date", "");
        values.put("Payment_Offer", 0.00);
        values.put("Post_Status", "Draft");
        values.put("Attach_File", "");
        values.put("Complete_Status", "Draft");
        values.put("User_ID", 1); // Replace with actual User ID

        long newRowId = database.insert("Assignment", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Draft Saved Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Save Draft!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}
