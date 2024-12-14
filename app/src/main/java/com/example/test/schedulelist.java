package com.example.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

public class schedulelist extends AppCompatActivity {

    private CalendarView calendarView;
    private TableLayout tableLayout;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulelist);

        // Initialize Views
        calendarView = findViewById(R.id.calendarViewSelect);
        tableLayout = findViewById(R.id.TableLayoutView);
        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);

        // Initialize Database
        db = new SQLiteHelper(this).getReadableDatabase();

        // Menu Button Navigation
        menuButton.setOnClickListener(v -> navigateTo(studentHome.class));

        // Notification Button Navigation
        notificationButton.setOnClickListener(v -> navigateTo(notification.class));

        // Calendar Date Selection Listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            fetchAndDisplaySchedule(selectedDate);
        });
    }

    // Navigate to another activity
    private void navigateTo(Class<?> activityClass) {
        startActivity(new android.content.Intent(this, activityClass));
    }

    // Fetch Schedule from SQLite and Display in TableLayout
    private void fetchAndDisplaySchedule(String date) {
        tableLayout.removeAllViews(); // Clear previous data

        // Query to fetch schedules for the selected date
        String query = "SELECT Title, Note, Time FROM Schedule WHERE Due_Date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date});

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(0);
                    String note = cursor.getString(1);
                    String time = cursor.getString(2);

                    // Add Data to TableLayout
                    addTableRow(title, note, time);
                }
            } else {
                Toast.makeText(this, "No tasks found for " + date, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
    }

    // Dynamically Add a Row to TableLayout
    private void addTableRow(String title, String note, String time) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // Title Column
        TextView titleText = createTextView(title);
        row.addView(titleText);

        // Note Column
        TextView noteText = createTextView(note);
        row.addView(noteText);

        // Time Column
        TextView timeText = createTextView(time);
        row.addView(timeText);

        // Add row to TableLayout
        tableLayout.addView(row);
    }

    // Utility Method to Create TextView for Table Rows
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        return textView;
    }

    // SQLite Helper Class
    private static class SQLiteHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
        private static final int DATABASE_VERSION = 1;

        public SQLiteHelper(schedulelist context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Schedule (" +
                    "Schedule_ID INTEGER PRIMARY KEY," +
                    "Title TEXT NOT NULL," +
                    "Note TEXT," +
                    "Time TIME," +
                    "Due_Date DATE," +
                    "Task_ID INTEGER," +
                    "FOREIGN KEY (Task_ID) REFERENCES Task(Task_ID))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Schedule");
            onCreate(db);
        }
    }
}
