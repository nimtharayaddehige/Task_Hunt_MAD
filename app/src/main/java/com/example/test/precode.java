package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class precode extends AppCompatActivity {

    private SQLiteDatabase database;
    private EditText pinCodeInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_precode);

        // Apply WindowInsets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the database and UI
        DatabaseHelper dbHelper = new DatabaseHelper(this, "TaskHunt_Database_SQLite", null, 1);
        database = dbHelper.getReadableDatabase();
        pinCodeInput = findViewById(R.id.pinCodeInput); // Replace with your EditText ID

        // Monitor PIN code input
        pinCodeInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Trigger when the user finishes editing
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
    private static class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
        private static final int DATABASE_VERSION = 1;

        // SQL command to create the User table
        private static final String CREATE_USER_TABLE =
                "CREATE TABLE User (" +
                        "User_ID INTEGER PRIMARY KEY, " +
                        "Name TEXT, " +
                        "Email TEXT, " +
                        "pin_code TEXT);";

        public DatabaseHelper(android.content.Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS User");
            onCreate(db);
        }
    }
}
