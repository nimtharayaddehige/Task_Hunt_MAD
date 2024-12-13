package com.example.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class studentHome extends AppCompatActivity {

    private SQLiteDatabase database;
    private Spinner taskCategorySpinner;
    private Button findButton;
    private TextView taskTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Initialize views
        taskCategorySpinner = findViewById(R.id.TaskCategoryTextField);
        findButton = findViewById(R.id.findButton);
        taskTextView = findViewById(R.id.taskTextView);

        // Open or create the database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

        // Set button click listener
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFutureTasks();
            }
        });
    }

    private void fetchFutureTasks() {
        String query = "SELECT * FROM Task WHERE Due_Date > date('now')";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                StringBuilder tasks = new StringBuilder();

                do {
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("Title"));
                    String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("Due_Date"));
                    String location = cursor.getString(cursor.getColumnIndexOrThrow("Location"));

                    tasks.append("Title: ").append(title).append("\n")
                            .append("Due Date: ").append(dueDate).append("\n")
                            .append("Location: ").append(location).append("\n\n");
                } while (cursor.moveToNext());

                taskTextView.setText(tasks.toString());
            } else {
                taskTextView.setText("No future tasks available.");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error fetching tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
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
}
