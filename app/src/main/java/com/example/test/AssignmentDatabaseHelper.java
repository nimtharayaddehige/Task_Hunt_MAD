package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AssignmentDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
    private static final int DATABASE_VERSION = 1;

    // SQL command to create the Assignment table
    private static final String CREATE_ASSIGNMENT_TABLE =
            "CREATE TABLE Assignment (" +
                    "Assignment_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Title TEXT NOT NULL, " +
                    "Description TEXT, " +
                    "Time TIME, " +
                    "Due_Date DATE, " +
                    "Payment_Offer REAL, " +
                    "Post_Status TEXT, " +
                    "Attach_File TEXT, " +
                    "Complete_Status TEXT, " +
                    "User_ID INTEGER, " +
                    "FOREIGN KEY (User_ID) REFERENCES User(User_ID));";

    public AssignmentDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Assignment table
        db.execSQL(CREATE_ASSIGNMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades
        db.execSQL("DROP TABLE IF EXISTS Assignment");
        onCreate(db);
    }
}
