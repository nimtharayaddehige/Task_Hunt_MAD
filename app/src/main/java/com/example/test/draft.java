package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class draft extends AppCompatActivity {

    private Button addTaskButton, draftButton;
    private TaskDatabaseHelper dbHelper;

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

        // Initialize the SQLite database helper
        dbHelper = new TaskDatabaseHelper(this);

        // Link Buttons to their IDs in the XML Layout
        addTaskButton = findViewById(R.id.addTaskButton);
        draftButton = findViewById(R.id.draftButton);

        // Add Task Button Functionality
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        // Draft Button Functionality
        draftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDraft();
            }
        });

    }

    // Function to add a task to SQLite
    void addTask() {
        // Create a Task object
        ContentValues taskData = new ContentValues();
        taskData.put("Requirement", "Requirement");
        taskData.put("Special_Note", "note");
        taskData.put("Contact_Name", "ContactName");
        taskData.put("Contact_Number", 1234567890);
        taskData.put("Due_Date", "2024-12-01");
        taskData.put("End_Date", "2024-12-31");
        taskData.put("End_Time", "23:59:59");
        taskData.put("Location", "Town");
        taskData.put("Service_Provider_ID", "Service_Provider_ID");
        taskData.put("Start_Date", "2024-12-01");
        taskData.put("Start_Time", "00:00:00");
        taskData.put("Task_Category", "T category");
        taskData.put("Task_ID", "T0001");
        taskData.put("Task_Status", "taskStatus1");
        taskData.put("Task_Type", "Task Type");
        taskData.put("Title", "Task Name");
        taskData.put("Vacancy_Document", "VDoc");

        // Insert task data into SQLite
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert("Task", null, taskData);

        if (result != -1) {
            Toast.makeText(this, "Task Added Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Add Task!", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to save a draft to SQLite
    private void saveDraft() {
        ContentValues draftData = new ContentValues();
        draftData.put("Task_ID", "DRAFT001");
        draftData.put("Title", "Draft Name");
        draftData.put("Task_Type", "Draft Type");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert("Task", null, draftData);

        if (result != -1) {
            Toast.makeText(this, "Draft Saved Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Save Draft!", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper class to manage SQLite database
    static class TaskDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite.db";
        private static final int DATABASE_VERSION = 1;

        public TaskDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Task (" +
                    "Task_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
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
                    "Service_Provider_ID TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Task");
            onCreate(db);
        }

        // CRUD Operations

        // Read all tasks
        public Cursor getAllTasks() {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM Task", null);
        }

        // Update a task by ID
        public boolean updateTask(String taskId, ContentValues values) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.update("Task", values, "Task_ID = ?", new String[]{taskId}) > 0;
        }

        // Delete a task by ID
        public boolean deleteTask(String taskId) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete("Task", "Task_ID = ?", new String[]{taskId}) > 0;
        }
    }
}
