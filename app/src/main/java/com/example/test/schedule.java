package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class schedule extends AppCompatActivity {

    private CalendarView calendarScheduleView;
    private Button easyFindButton;
    private DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Initialize database helper and UI components
        dbHelper = new DBHelper(this);
        calendarScheduleView = findViewById(R.id.calendarScheduleView);
        easyFindButton = findViewById(R.id.EasyFindButton);

        // Initialize navigation buttons
        Button taskButton = findViewById(R.id.TaskButton);
        Button myTaskButton = findViewById(R.id.MyTaskButton);
        Button scheduleButton = findViewById(R.id.ScheduleButton);
        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);

        // Highlight task due dates on the calendar
        highlightDueDates();

        // Set on-click listeners for navigation buttons
        taskButton.setOnClickListener(v -> navigateToActivity(task.class));
        myTaskButton.setOnClickListener(v -> navigateToActivity(taskmyschedulelist.class));
        scheduleButton.setOnClickListener(v -> navigateToActivity(schedulemytask.class));
        menuButton.setOnClickListener(v -> navigateToActivity(studentHome.class));
        notificationButton.setOnClickListener(v -> navigateToActivity(notification.class));

        // Handle EasyFindButton click for searching task or schedule
        easyFindButton.setOnClickListener(v -> handleEasyFindButtonClick());

        // Handle window insets for padding (e.g., status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to navigate to another activity
    private void navigateToActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(schedule.this, destinationActivity);
        startActivity(intent);
    }

    // Method to highlight due dates on the calendar
    private void highlightDueDates() {
        List<String> dueDates = dbHelper.getTaskDueDates();

        // Highlight each due date on the CalendarView
        // This is a basic approach, consider using a custom calendar library for better styling
        for (String dueDate : dueDates) {
            // Here you would implement the logic to mark the date in the calendar view
            // CalendarView does not support direct date decorations, so consider using a library
            // or override onDateChangeListener to modify the UI (or use a DateDecorator if you add a library)
            // Toast message for testing the fetched dates
            Toast.makeText(this, "Due date found: " + dueDate, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle EasyFindButton click event
    private void handleEasyFindButtonClick() {
        long selectedDateMillis = calendarScheduleView.getDate(); // Get the selected date in milliseconds
        String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selectedDateMillis));

        // Search the Task table for matching due dates
        searchTasksByDueDate(selectedDate);

        // Search the Schedule table for matching due dates
        searchSchedulesByDueDate(selectedDate);
    }

    // Method to search for tasks based on the selected due date
    private void searchTasksByDueDate(String selectedDate) {
        List<String> taskDueDates = dbHelper.getTaskDueDates();
        boolean taskFound = false;

        for (String dueDate : taskDueDates) {
            if (dueDate.equals(selectedDate)) {
                // Show the Task ID or any other relevant info
                Toast.makeText(this, "Task found for this date", Toast.LENGTH_SHORT).show();
                taskFound = true;
                break;
            }
        }

        if (!taskFound) {
            Toast.makeText(this, "Task NOT FOUND", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to search for schedules based on the selected due date
    private void searchSchedulesByDueDate(String selectedDate) {
        List<String> scheduleDueDates = dbHelper.getScheduleDueDates(); // Implement this method in DBHelper
        boolean scheduleFound = false;

        for (String dueDate : scheduleDueDates) {
            if (dueDate.equals(selectedDate)) {
                // Show the Schedule ID or any other relevant info
                Toast.makeText(this, "Schedule found for this date", Toast.LENGTH_SHORT).show();
                scheduleFound = true;
                break;
            }
        }

        if (!scheduleFound) {
            Toast.makeText(this, "Schedule NOT FOUND", Toast.LENGTH_SHORT).show();
        }
    }
}
