package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class task extends AppCompatActivity {

    private TextView detailsView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Initialize views
        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);
        ImageView subscriptionIcon = findViewById(R.id.subscriptionIcon);

        Button allTaskButton = findViewById(R.id.allTaskButton);
        Button physicalButton = findViewById(R.id.physicalButton);
        Button remoteButton = findViewById(R.id.remoteButton);
        Button completeTaskButton = findViewById(R.id.completeTaskButton);

        detailsView = findViewById(R.id.detailsView);

        // Initialize database
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        db = dbHelper.getReadableDatabase();

        // Navigation Buttons
        menuButton.setOnClickListener(v -> navigateTo(studentHome.class));
        notificationButton.setOnClickListener(v -> navigateTo(notification.class));
        subscriptionIcon.setOnClickListener(v -> navigateTo(Subscriptionplan.class));

        // Task Buttons with Queries
        allTaskButton.setOnClickListener(v -> loadTasks("SELECT * FROM Task"));
        physicalButton.setOnClickListener(v -> loadTasks("SELECT * FROM Task WHERE Task_Status = 'Physical'"));
        remoteButton.setOnClickListener(v -> loadTasks("SELECT * FROM Task WHERE Task_Status = 'Remote'"));
        completeTaskButton.setOnClickListener(v -> loadTasks("SELECT * FROM Task WHERE Task_Status = 'Complete'"));
    }

    // Method to navigate to another activity
    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    // Method to load tasks based on query and display in detailsView
    private void loadTasks(String query) {
        Cursor cursor = db.rawQuery(query, null);
        StringBuilder result = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get data from each column
                String title = cursor.getString(cursor.getColumnIndexOrThrow("Title"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("Task_Category"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("Start_Date"));
                String endDate = cursor.getString(cursor.getColumnIndexOrThrow("End_Date"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("Task_Status"));
                String contact = cursor.getString(cursor.getColumnIndexOrThrow("Contact_Name"));

                // Append details
                result.append("Title: ").append(title).append("\n")
                        .append("Category: ").append(category).append("\n")
                        .append("Start Date: ").append(startDate).append("\n")
                        .append("End Date: ").append(endDate).append("\n")
                        .append("Status: ").append(status).append("\n")
                        .append("Contact: ").append(contact).append("\n\n");
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            result.append("No tasks found.");
        }

        // Set result to TextView
        detailsView.setText(result.toString());
    }

    // SQLiteHelper Class
    static class SQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite.db";
        private static final int DATABASE_VERSION = 1;

        // SQL Query to Create Task Table
        private static final String TABLE_TASK = "CREATE TABLE Task ("
                + "Task_ID INTEGER PRIMARY KEY,"
                + "Title TEXT NOT NULL,"
                + "Task_Type TEXT,"
                + "Task_Category TEXT,"
                + "Start_Date DATE,"
                + "End_Date DATE,"
                + "Start_Time TIME,"
                + "End_Time TIME,"
                + "Due_Date DATE,"
                + "Pay_Amount REAL,"
                + "Task_Status TEXT,"
                + "Vacancy_Document TEXT,"
                + "Requirement TEXT,"
                + "Special_Note TEXT,"
                + "Location TEXT,"
                + "Contact_Name TEXT,"
                + "Contact_Number TEXT,"
                + "Service_Provider_ID TEXT,"
                + "FOREIGN KEY (Service_Provider_ID) REFERENCES Service_Provider(Company_Name));";

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_TASK); // Create the Task Table
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if it exists
            db.execSQL("DROP TABLE IF EXISTS Task");
            // Create the table again
            onCreate(db);
        }
    }
}
