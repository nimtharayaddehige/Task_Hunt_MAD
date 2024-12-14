package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables for User, Task, Schedule, etc.
        db.execSQL("CREATE TABLE IF NOT EXISTS Task (" +
                "Task_ID INTEGER PRIMARY KEY," +
                "Title TEXT NOT NULL," +
                "Due_Date DATE," +
                "Task_Status TEXT" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS Schedule (" +
                "Schedule_ID INTEGER PRIMARY KEY," +
                "Title TEXT NOT NULL," +
                "Due_Date DATE," +
                "Task_ID INTEGER," +
                "FOREIGN KEY (Task_ID) REFERENCES Task(Task_ID)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL("DROP TABLE IF EXISTS Schedule");
        onCreate(db);
    }

    // Fetch all task due dates
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

    // Fetch all schedule due dates
    public List<String> getScheduleDueDates() {
        List<String> dueDates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Due_Date FROM Schedule", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String dueDate = cursor.getString(cursor.getColumnIndex("Due_Date"));
                dueDates.add(dueDate);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return dueDates;
    }

    // Insert a task into the database
    public long insertTask(String title, String dueDate, String taskStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Due_Date", dueDate);
        values.put("Task_Status", taskStatus);

        return db.insert("Task", null, values);
    }

    // Insert a schedule into the database
    public long insertSchedule(String title, String dueDate, int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Due_Date", dueDate);
        values.put("Task_ID", taskId);

        return db.insert("Schedule", null, values);
    }
}
