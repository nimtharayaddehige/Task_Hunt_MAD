package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class precode extends AppCompatActivity {

    private EditText pinCodeInput;
    private Button proceedButton;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precode);



        // Initialize views
        pinCodeInput = findViewById(R.id.pinCodeInput);
        proceedButton = findViewById(R.id.proceedButton);

        // Initialize the database helper and get readable database
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        // Set listener on the proceed button
        proceedButton.setOnClickListener(v -> {
            String pinCode = pinCodeInput.getText().toString().trim();
            checkPinCode(pinCode);
        });

        // Set listener for pin code input to check when user stops typing
        pinCodeInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Trigger when user finishes editing
                String enteredPinCode = pinCodeInput.getText().toString().trim();
                if (!enteredPinCode.isEmpty()) {
                    checkPinCode(enteredPinCode);
                }
            }
        });
    }

    /**
     * Checks if the entered PIN code exists in the database.
     *
     * @param pinCode The PIN code entered by the user.
     */
    private void checkPinCode(String pinCode) {
        // Validate the pin code length
        if (pinCode.length() != 4) {
            Toast.makeText(this, "PIN must be 4 digits.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query the database to see if the pin code exists
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE pin_code = ?", new String[]{pinCode});
        if (cursor.moveToFirst()) {
            cursor.close();
            navigateToManageMyAssignment();
        } else {
            cursor.close();
            Toast.makeText(this, "Invalid PIN code.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navigates to the ManageMyAssignment activity.
     */
    private void navigateToManageMyAssignment() {
        Intent intent = new Intent(precode.this, reenterpasscode.class);
        startActivity(intent);
        finish(); // Optional: close the current activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }

    /**
     * SQLiteOpenHelper for managing the database.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
        private static final int DATABASE_VERSION = 1;

        // SQL command to create the User table
        private static final String CREATE_USER_TABLE =
                "CREATE TABLE User (" +
                        "User_ID INTEGER PRIMARY KEY, " +
                        "Name TEXT, " +
                        "Email TEXT, " +
                        "pin_code TEXT);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create the User table
            db.execSQL(CREATE_USER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop old User table and recreate it
            db.execSQL("DROP TABLE IF EXISTS User");
            onCreate(db);
        }
    }
}
