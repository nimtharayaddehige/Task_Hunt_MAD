package com.example.test;

<<<<<<< Updated upstream
=======
import android.annotation.SuppressLint;
import android.content.Intent;
>>>>>>> Stashed changes
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
<<<<<<< Updated upstream
import android.widget.Button;
=======
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
>>>>>>> Stashed changes
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream
=======
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
>>>>>>> Stashed changes

public class studentHome extends AppCompatActivity {

    private SQLiteDatabase database;
    private Spinner taskCategorySpinner;
    private Button findButton;
    private TextView taskTextView;
<<<<<<< Updated upstream

=======
    private EditText searchBar;
    private ImageButton menuButton;
    private DrawerLayout drawerLayout;
    private LinearLayout menuDrawer;

    @SuppressLint("MissingInflatedId")
>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Initialize views
        taskCategorySpinner = findViewById(R.id.TaskCategoryTextField);
        findButton = findViewById(R.id.findButton);
        taskTextView = findViewById(R.id.taskTextView);
<<<<<<< Updated upstream
=======
        searchBar = findViewById(R.id.searchBar);
        menuButton = findViewById(R.id.menuButton);
        drawerLayout = findViewById(R.id.drawerLayout);
        menuDrawer = findViewById(R.id.menuDrawer);
>>>>>>> Stashed changes

        // Open or create the database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

<<<<<<< Updated upstream
        // Set button click listener
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFutureTasks();
=======
        // Populate spinner with task categories
        populateTaskCategorySpinner();

        // Set button click listener for Find
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFutureTasksByCategory();
            }
        });

        // Set menu button click listener
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenuDrawer();
            }
        });

        // Setup menu options
        setupMenuOptions();

        // Set search functionality
        setupSearchFunctionality();
    }

    private void toggleMenuDrawer() {
        if (drawerLayout.isDrawerOpen(menuDrawer)) {
            drawerLayout.closeDrawer(menuDrawer);
        } else {
            drawerLayout.openDrawer(menuDrawer);
        }
    }

    private void setupMenuOptions() {
        ListView menuListView = findViewById(R.id.menuListView);
        String[] menuItems = {"Assignments", "Tasks", "Schedule My Task", "Profile", "Logout"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItems);
        menuListView.setAdapter(adapter);

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Assignments
                        startActivity(new Intent(studentHome.this, managemyassignment.class));
                        break;
                    case 1: // Tasks
                        startActivity(new Intent(studentHome.this, task.class));
                        break;
                    case 2: // Schedule My Task
                        startActivity(new Intent(studentHome.this, schedulemytask.class));
                        break;
                    case 3: // Profile
                        startActivity(new Intent(studentHome.this, profile.class));
                        break;
                    case 4: // Logout
                        startActivity(new Intent(studentHome.this, welcome1.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(menuDrawer);
>>>>>>> Stashed changes
            }
        });
    }

<<<<<<< Updated upstream
    private void fetchFutureTasks() {
        String query = "SELECT * FROM Task WHERE Due_Date > date('now')";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, null);
=======
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
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
=======
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

>>>>>>> Stashed changes
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}
