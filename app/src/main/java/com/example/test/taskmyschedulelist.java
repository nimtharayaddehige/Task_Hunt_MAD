package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class taskmyschedulelist extends AppCompatActivity {

    private SQLiteDatabase database;
    private TableLayout tableLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskmyschedulelist);

        // Initialize the SQLite database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

        // Find views
        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);
        ImageButton backButton2 = findViewById(R.id.backButton2);
        tableLayout = findViewById(R.id.tableLayout); // TableLayout defined in XML

        // Set button click listeners
        menuButton.setOnClickListener(view -> navigateTo(studentHome.class));
        notificationButton.setOnClickListener(view -> navigateTo(notification.class));
        backButton2.setOnClickListener(view -> navigateTo(schedule.class));

        // Load schedule data into the table view
        loadScheduleData();
    }

    private void navigateTo(Class<?> targetClass) {
        Intent intent = new Intent(taskmyschedulelist.this, targetClass);
        startActivity(intent);
    }

    private void loadScheduleData() {
        // Query to fetch all data from the Schedule table
        String query = "SELECT * FROM Schedule";
        Cursor cursor = database.rawQuery(query, null);

        // Clear the table layout before adding new rows
        tableLayout.removeAllViews();

        // Add table header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("ID"));
        headerRow.addView(createTextView("Title"));
        headerRow.addView(createTextView("Note"));
        headerRow.addView(createTextView("Time"));
        headerRow.addView(createTextView("Due Date"));
        tableLayout.addView(headerRow);

        // Iterate through the cursor and populate the table
        if (cursor.moveToFirst()) {
            do {
                TableRow row = new TableRow(this);

                row.addView(createTextView(cursor.getString(0))); // Schedule_ID
                row.addView(createTextView(cursor.getString(1))); // Title
                row.addView(createTextView(cursor.getString(2))); // Note
                row.addView(createTextView(cursor.getString(3))); // Time
                row.addView(createTextView(cursor.getString(4))); // Due Date

                tableLayout.addView(row);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(16, 16, 16, 16);
        return textView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
