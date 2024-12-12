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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class draft extends AppCompatActivity {

    private Button addTaskButton, draftButton;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_draft);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Task");

        //Link Buttons to their IDs in the XML Layout
        addTaskButton = findViewById(R.id.addTaskButton);
        draftButton = findViewById(R.id.draftButton);

        //Add Task Button Functionality
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });

        //Draft Button Functionality
        draftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDraft();
            }
        });

    }

    //Function to add a task to Firebase
    void addTask() {
        // Create a reference to the "tasks" table in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Task");

        // Generate unique Task ID
        String taskId = "T0001"; // Replace with dynamically generated ID if needed

        // Create a Task object
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("Requirement", "Requirement");
        taskData.put("Special_Note", "note");
        taskData.put("Contact_Name", "ContactName");
        taskData.put("Contact_Number", 1234567890);
        taskData.put("Due_Date", 0);
        taskData.put("End_Date", "2024-12-31T00:00:00Z");
        taskData.put("End_Time", "2024-12-31T00:00:00Z");
        taskData.put("Location", new HashMap<String, Object>() {{
            put("location_name", "Town");
        }});
        taskData.put("Service_Provider_ID", "Service_Provider_ID");
        taskData.put("Start_Date", "2024-12-01T00:00:00Z");
        taskData.put("Start_Time", "2024-12-01T00:00:00Z");
        taskData.put("Task_Category", "T category");
        taskData.put("Task_ID", taskId);
        taskData.put("Task_Status", "taskStatus1");
        taskData.put("Task_Type", "Task Type");
        taskData.put("Title", "Task Name");
        taskData.put("Vacancy_Document", "VDoc");

        // Push the task data to Firebase
        databaseReference.child(taskId).setValue(taskData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Task Added Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to Add Task!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Function to add a draft to Firebase
    private void saveDraft() {
        String draftId = databaseReference.push().getKey(); // Generate unique ID
        task draft = new task(draftId, "Draft Name", "Draft Description", true); // Example draft

        databaseReference.child(draftId).setValue(draft);

    }
}