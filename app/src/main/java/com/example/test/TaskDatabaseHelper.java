package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_TASK = "Task";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_TASK_CATEGORY = "Task_Category";
    public static final String COLUMN_DUE_DATE = "Due_Date";
    public static final String COLUMN_LOCATION = "Location";

    // Create table SQL statement
    private static final String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_TASK_CATEGORY + " TEXT, "
            + COLUMN_DUE_DATE + " TEXT, "
            + COLUMN_LOCATION + " TEXT);";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_TASK);  // Create the task table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    // Method to add a task
    public long addTask(String title, String taskCategory, String dueDate, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TASK_CATEGORY, taskCategory);
        values.put(COLUMN_DUE_DATE, dueDate);
        values.put(COLUMN_LOCATION, location);
        return db.insert(TABLE_TASK, null, values);
    }

    // Method to get all tasks
    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASK, null);
    }

    // Method to get tasks by category
    public Cursor getTasksByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_CATEGORY + " = ?";
        return db.rawQuery(query, new String[]{category});
    }

    // Method to fetch future tasks
    public Cursor getFutureTasks(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_DUE_DATE + " > ?";
        return db.rawQuery(query, new String[]{currentDate});
    }

    // Method to search tasks by keyword
    public Cursor searchTasks(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE "
                + COLUMN_TITLE + " LIKE ? OR "
                + COLUMN_TASK_CATEGORY + " LIKE ? OR "
                + COLUMN_LOCATION + " LIKE ?";
        return db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});
    }

    // Method to update task data
    public int updateTask(long taskId, String title, String taskCategory, String dueDate, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TASK_CATEGORY, taskCategory);
        values.put(COLUMN_DUE_DATE, dueDate);
        values.put(COLUMN_LOCATION, location);
        return db.update(TABLE_TASK, values, COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
    }

    // Method to delete a task
    public int deleteTask(long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TASK, COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
    }
}
