package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class scheduletask extends AppCompatActivity {

    private CalendarView calendarScheduleView;
    private Button easyFindButton;
    private TextView inProgressTextView, completeTextView;
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
        inProgressTextView = findViewById(R.id.InProgress);
        completeTextView = findViewById(R.id.Complete);

        // Initialize navigation buttons
        Button taskButton = findViewById(R.id.TaskButton);
        Button myTaskButton = findViewById(R.id.MyTaskButton);
        Button scheduleButton = findViewById(R.id.ScheduleButton);
        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);

        // Highlight due dates on the calendar
        highlightDueDates();

        // Set on-click listeners for navigation buttons
        taskButton.setOnClickListener(v -> navigateToActivity(task.class));
        myTaskButton.setOnClickListener(v -> navigateToActivity(schedulemytask.class));
        scheduleButton.setOnClickListener(v -> navigateToActivity(scheduletask.class));
        menuButton.setOnClickListener(v -> navigateToActivity(studentHome.class));
        notificationButton.setOnClickListener(v -> navigateToActivity(notification.class));

        // Handle EasyFindButton click to search tasks by selected date
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
        Intent intent = new Intent(scheduletask.this, destinationActivity);
        startActivity(intent);
    }

    // Method to highlight due dates on the CalendarView
    private void highlightDueDates() {
        List<String> dueDates = dbHelper.getTaskDueDates();

        // Iterate through the due dates and display them in a Toast (or color the dates in CalendarView)
        for (String dueDate : dueDates) {
            // Display each due date (could be improved by using a custom calendar view or library)
            Toast.makeText(this, "Due date found: " + dueDate, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle EasyFindButton click event
    private void handleEasyFindButtonClick() {
        // Get the selected date in milliseconds
        long selectedDateMillis = calendarScheduleView.getDate();
        String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selectedDateMillis));

        // First, search for "PENDING" tasks
        searchTasksByStatus(selectedDate, "PENDING");

        // Then, search for "COMPLETED" tasks
        searchTasksByStatus(selectedDate, "COMPLETED");
    }

    // Method to search for tasks based on selected date and task status
    private void searchTasksByStatus(String selectedDate, String status) {
        // Fetch tasks that match the selected date and the given status
        List<Task> tasks = dbHelper.getTasksByStatusAndDate(selectedDate, status);
        if (!tasks.isEmpty()) {
            // Display task IDs in the respective section
            StringBuilder taskIds = new StringBuilder();
            for (Task task : tasks) {
                taskIds.append(task.getTaskId()).append("\n");
            }

            if (status.equalsIgnoreCase("PENDING")) {
                // Show the pending tasks in the In Progress section
                inProgressTextView.setText("In Progress:\n" + taskIds.toString());
            } else if (status.equalsIgnoreCase("COMPLETED")) {
                // Show the completed tasks in the Complete section
                completeTextView.setText("Completed:\n" + taskIds.toString());
            }
        } else {
            // If no tasks are found, show "NOT FOUND"
            Toast.makeText(this, "NOT FOUND", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper class for database operations
    public class DBHelper extends android.database.sqlite.SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
        private static final int DATABASE_VERSION = 1;

        // Constructor
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create all necessary tables
            db.execSQL("CREATE TABLE IF NOT EXISTS User (" +
                    "User_ID INTEGER PRIMARY KEY," +
                    "First_Name TEXT NOT NULL," +
                    "Last_Name TEXT NOT NULL," +
                    "Date_Of_Birth DATE," +
                    "Contact_Number TEXT," +
                    "Email TEXT UNIQUE," +
                    "Password TEXT NOT NULL," +
                    "Location TEXT," +
                    "Pin_Code TEXT" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Bank_Details (" +
                    "Bank_Card_No TEXT PRIMARY KEY," +
                    "Card_Holder_Name TEXT NOT NULL," +
                    "Expire_Date DATE NOT NULL," +
                    "CVV INTEGER NOT NULL," +
                    "User_ID INTEGER," +
                    "FOREIGN KEY (User_ID) REFERENCES User(User_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Service_Provider (" +
                    "Company_Name TEXT PRIMARY KEY," +
                    "Company_Type TEXT," +
                    "User_ID INTEGER," +
                    "FOREIGN KEY (User_ID) REFERENCES User(User_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Task (" +
                    "Task_ID INTEGER PRIMARY KEY," +
                    "Title TEXT NOT NULL," +
                    "Task_Type TEXT," +
                    "Task_Category TEXT," +
                    "Start_Date DATE," +
                    "End_Date DATE," +
                    "Start_Time TIME," +
                    "End_Time TIME," +
                    "Due_Date DATE," +
                    "Pay_Amount REAL," +
                    "Task_Status TEXT," +
                    "Vacancy_Document TEXT," +
                    "Requirement TEXT," +
                    "Special_Note TEXT," +
                    "Location TEXT," +
                    "Contact_Name TEXT," +
                    "Contact_Number TEXT," +
                    "Service_Provider_ID TEXT," +
                    "FOREIGN KEY (Service_Provider_ID) REFERENCES Service_Provider(Company_Name)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Schedule (" +
                    "Schedule_ID INTEGER PRIMARY KEY," +
                    "Title TEXT NOT NULL," +
                    "Note TEXT," +
                    "Time TIME," +
                    "Due_Date DATE," +
                    "Task_ID INTEGER," +
                    "FOREIGN KEY (Task_ID) REFERENCES Task(Task_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Free_Schedule (" +
                    "Free_Schedule_ID INTEGER PRIMARY KEY," +
                    "Template TEXT," +
                    "Free_Hours INTEGER," +
                    "Schedule_ID INTEGER," +
                    "FOREIGN KEY (Schedule_ID) REFERENCES Schedule(Schedule_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Assignment (" +
                    "Assignment_ID INTEGER PRIMARY KEY," +
                    "Title TEXT NOT NULL," +
                    "Description TEXT," +
                    "Time TIME," +
                    "Due_Date DATE," +
                    "Payment_Offer REAL," +
                    "Post_Status TEXT," +
                    "Attach_File TEXT," +
                    "Complete_Status TEXT," +
                    "User_ID INTEGER," +
                    "FOREIGN KEY (User_ID) REFERENCES User(User_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Assignment_Feedback (" +
                    "A_Feedback_ID INTEGER PRIMARY KEY," +
                    "Feedback TEXT," +
                    "Reviews TEXT," +
                    "Assignment_ID INTEGER," +
                    "FOREIGN KEY (Assignment_ID) REFERENCES Assignment(Assignment_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Student_Feedback (" +
                    "S_Feedback_ID INTEGER PRIMARY KEY," +
                    "Feedback TEXT," +
                    "Reviews TEXT," +
                    "User_ID INTEGER," +
                    "FOREIGN KEY (User_ID) REFERENCES User(User_ID)" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS Service_Provider_Feedback (" +
                    "SP_Feedback_ID INTEGER PRIMARY KEY," +
                    "Feedback TEXT," +
                    "Reviews TEXT," +
                    "Service_Provider_ID TEXT," +
                    "FOREIGN KEY (Service_Provider_ID) REFERENCES Service_Provider(Company_Name)" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Bank_Details");
            db.execSQL("DROP TABLE IF EXISTS Service_Provider");
            db.execSQL("DROP TABLE IF EXISTS Task");
            db.execSQL("DROP TABLE IF EXISTS Schedule");
            db.execSQL("DROP TABLE IF EXISTS Free_Schedule");
            db.execSQL("DROP TABLE IF EXISTS Assignment");
            db.execSQL("DROP TABLE IF EXISTS Assignment_Feedback");
            db.execSQL("DROP TABLE IF EXISTS Student_Feedback");
            db.execSQL("DROP TABLE IF EXISTS Service_Provider_Feedback");
            onCreate(db);
        }

        // Fetch the due dates from the Task table
        public List<String> getTaskDueDates() {
            List<String> dueDates = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT Due_Date FROM Task", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String dueDate = cursor.getString(cursor.getColumnIndex("Due_Date"));
                    dueDates.add(dueDate);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return dueDates;
        }

        // Fetch tasks based on status and due date
        public List<Task> getTasksByStatusAndDate(String selectedDate, String status) {
            List<Task> tasks = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT Task_ID FROM Task WHERE Due_Date = ? AND LOWER(Task_Status) = ?",
                    new String[]{selectedDate, status.toLowerCase()});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int taskId = cursor.getInt(cursor.getColumnIndex("Task_ID"));
                    tasks.add(new Task(taskId));
                } while (cursor.moveToNext());
                cursor.close();
            }
            return tasks;
        }
    }

    // Task class (simplified)
    public static class Task {
        private int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }
    }
}
