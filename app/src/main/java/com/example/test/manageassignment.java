package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class manageassignment extends AppCompatActivity {

    private Button addTaskButton, viewTasksButton, logoutButton;

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

        // Initialize buttons
        addTaskButton = findViewById(R.id.addTaskButton);
<<<<<<< Updated upstream
        viewTasksButton = findViewById(R.id.draftButton);
=======
        viewTasksButton = findViewById(R.id.getStartedButton);
>>>>>>> Stashed changes

        // Set up button click listeners
        addTaskButton.setOnClickListener(v -> navigateToAddTask());
        viewTasksButton.setOnClickListener(v -> navigateToViewTasks());

    }

    private void navigateToAddTask() {
        // Navigate to Add Task Activity
        Intent intent = new Intent(this, task.class);
        startActivity(intent);
        Toast.makeText(this, "Navigating to Add Task", Toast.LENGTH_SHORT).show();
    }

    private void navigateToViewTasks() {
        // Navigate to View Tasks Activity
        Intent intent = new Intent(this, draft.class);
        startActivity(intent);
        Toast.makeText(this, "Navigating to View Tasks", Toast.LENGTH_SHORT).show();
    }

    private void handleLogout() {
        // Handle logout logic (e.g., clear user session, navigate to login screen)
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, loginpage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
