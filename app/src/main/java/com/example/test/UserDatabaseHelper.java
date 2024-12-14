package com.example.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends TaskDatabaseHelper {

    // Constructor to pass the context to the parent TaskDatabaseHelper
    public UserDatabaseHelper(loginpage context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

        // Create User table if it does not exist
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS User (" +
                "User_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "First_Name TEXT NOT NULL, " +
                "Last_Name TEXT NOT NULL, " +
                "Date_Of_Birth DATE, " +
                "Contact_Number TEXT, " +
                "Email TEXT UNIQUE, " +
                "Password TEXT NOT NULL, " +
                "Location TEXT, " +
                "Pin_Code TEXT)";
        db.execSQL(createUserTableQuery);
    }

    // Method to validate user login (email and password)
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the database for matching email and password
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Email = ? AND Password = ?", new String[]{email, password});

        boolean isValid = cursor.moveToFirst(); // Move to the first record if exists
        cursor.close(); // Always close the cursor to avoid memory leaks
        return isValid; // Return true if valid, otherwise false
    }

    // Method to add a new user to the database
    public long addUser(String firstName, String lastName, String dob, String contactNumber, String email, String password, String location, String pinCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("First_Name", firstName);
        values.put("Last_Name", lastName);
        values.put("Date_Of_Birth", dob);
        values.put("Contact_Number", contactNumber);
        values.put("Email", email);
        values.put("Password", password);
        values.put("Location", location);
        values.put("Pin_Code", pinCode);

        // Insert the new user into the User table
        return db.insert("User", null, values);
    }
}
