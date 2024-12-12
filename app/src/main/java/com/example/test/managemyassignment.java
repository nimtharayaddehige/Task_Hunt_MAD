package com.example.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class managemyassignment extends AppCompatActivity {

    private Button addAssignmentButton, saveDraftButton;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_managemyassignment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Assignment");

        // Link Buttons to their IDs in the XML Layout
        addAssignmentButton = findViewById(R.id.addTaskButton);
        saveDraftButton = findViewById(R.id.draftButton);

        // Add Assignment Button Functionality
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssignment();
            }
        });

        // Save Draft Button Functionality
        saveDraftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDraft();
            }
        });
    }

    // Function to add an assignment to Firebase
    private void addAssignment() {
        // Create a reference to the "assignments" table in Firebase
        DatabaseReference assignmentReference = FirebaseDatabase.getInstance().getReference("Assignment");

        // Generate unique Assignment ID
        String assignmentId = "A0001"; // Replace with dynamically generated ID if needed

        // Create an Assignment object
        Map<String, Object> assignmentData = new HashMap<>();
        assignmentData.put("Title", "Assignment Title");
        assignmentData.put("Description", "Assignment Description");
        assignmentData.put("Due_Date", "2024-12-15T00:00:00Z");
        assignmentData.put("Status", "Pending");
        assignmentData.put("Assigned_To", "User123");

        // Push the assignment data to Firebase
        assignmentReference.child(assignmentId).setValue(assignmentData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Assignment Added Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to Add Assignment!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to save a draft to Firebase
    private void saveDraft() {
        String draftId = databaseReference.push().getKey(); // Generate unique ID

        Map<String, Object> draftData = new HashMap<>();
        draftData.put("Draft_ID", draftId);
        draftData.put("Title", "Draft Title");
        draftData.put("Description", "Draft Description");
        draftData.put("Is_Draft", true);

        databaseReference.child(draftId).setValue(draftData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Draft Saved Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to Save Draft!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
