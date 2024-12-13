package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class schedulemytask extends AppCompatActivity {

    private CalendarView calendarView;
    private Button taskButton, myTaskButton, scheduleButton;
    private ImageButton menuButton;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulemytask);

        // Initialize views
        calendarView = findViewById(R.id.calendarView2);
        taskButton = findViewById(R.id.taskButton);
        myTaskButton = findViewById(R.id.myTaskButton);
        scheduleButton = findViewById(R.id.scheduleButton);
        menuButton = findViewById(R.id.menuButton);

        // Open or create the database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

        // Set button click listeners
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(schedulemytask.this, Subscriptionplan.class);
                startActivity(intent);
            }
        });

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightDatesFromTable("Task", "Start_Date");
            }
        });

        myTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightDatesFromTable("Assignment", "Due_Date");
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(schedulemytask.this, schedule.class);
                startActivity(intent);
            }
        });
    }

    private void highlightDatesFromTable(String tableName, String dateColumn) {
        String query = "SELECT " + dateColumn + " FROM " + tableName;
        Cursor cursor = database.rawQuery(query, null);

        List<Long> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                try {
                    String dateString = cursor.getString(0);
                    Date date = sdf.parse(dateString);
                    if (date != null) {
                        dateList.add(date.getTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (long date : dateList) {
            calendarView.setDate(date); // Highlights the specific date
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
