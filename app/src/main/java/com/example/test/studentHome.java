package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

public class studentHome extends AppCompatActivity {

    private SQLiteDatabase database;
    private Spinner taskCategorySpinner;
    private Button findButton;
    private TextView taskTextView;
    private EditText searchBar;
    private ImageButton menuButton;
    private DrawerLayout drawerLayout;
    private LinearLayout menuDrawer;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Initialize views
        taskCategorySpinner = findViewById(R.id.textView21);
        findButton = findViewById(R.id.findButton);
        taskTextView = findViewById(R.id.textView22);
        searchBar = findViewById(R.id.searchBar);
        menuButton = findViewById(R.id.menuButton);


        // Open or create the database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

        // Populate spinner with task categories
        populateTaskCategorySpinner();

        // Set button click listeners
        findButton.setOnClickListener(v -> fetchFutureTasksByCategory());
        menuButton.setOnClickListener(v -> refreshPage());

        // Set search functionality
        setupSearchFunctionality();
    }

    private void refreshPage() {
        // Refresh the page when the menu button is clicked
        recreate(); // This recreates the activity, effectively "refreshing" the page
    }


    private void setupSearchFunctionality() {
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String keyword = searchBar.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchTasks(keyword);
            }
            return false;
        });
    }

    private void populateTaskCategorySpinner() {
        List<String> categories = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT DISTINCT Task_Category FROM Task", null);
            while (cursor.moveToNext()) {
                categories.add(cursor.getString(0));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading categories: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskCategorySpinner.setAdapter(adapter);
    }

    private void fetchFutureTasksByCategory() {
        String selectedCategory = taskCategorySpinner.getSelectedItem().toString();
        String query = "SELECT * FROM Task WHERE Task_Category = ? AND Due_Date > date('now')";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, new String[]{selectedCategory});

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

    private void searchTasks(String keyword) {
        String query = "SELECT * FROM Task WHERE Title LIKE ? OR Task_Category LIKE ? OR Location LIKE ?";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});

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
                taskTextView.setText("No tasks found.");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error searching tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
