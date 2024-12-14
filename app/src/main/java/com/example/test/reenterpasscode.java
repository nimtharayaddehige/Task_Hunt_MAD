package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class reenterpasscode extends AppCompatActivity {

    private SQLiteDatabase database;
    private EditText reenterPinInput, newPinCodeInput;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reenterpasscode);

        // Apply edge-to-edge UI padding for system bars
        applyEdgeToEdgeUI();

        // Initialize UI components
        initializeUIComponents();

        // Initialize database connection
        initializeDatabase();

        // Set listener for the reset button
        resetButton.setOnClickListener(v -> handleResetButtonClick());
    }

    /**
     * Applies edge-to-edge UI padding for system bars.
     */
    private void applyEdgeToEdgeUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Initializes UI components.
     */
    @SuppressLint("WrongViewCast")
    private void initializeUIComponents() {

        resetButton = findViewById(R.id.resetBtn); // Find the reset button
    }

    /**
     * Initializes the database connection.
     */
    private void initializeDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Handles reset button click to update the PIN code.
     */
    private void handleResetButtonClick() {
        String newPinCode = newPinCodeInput.getText().toString().trim();
        String reenteredPinCode = reenterPinInput.getText().toString().trim();

        if (!newPinCode.isEmpty() && newPinCode.equals(reenteredPinCode)) {
            updatePinCode(newPinCode);
        } else {
            Toast.makeText(this, "PIN codes do not match or are invalid.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates the PIN code in the User table.
     *
     * @param newPinCode The new PIN code entered by the user.
     */
    private void updatePinCode(String newPinCode) {
        ContentValues values = new ContentValues();
        values.put("pin_code", newPinCode);

        // Assuming 'User_ID' is the primary key for identifying users
        String whereClause = "User_ID = ?";
        String[] whereArgs = new String[]{"1"}; // Assuming user with ID '1', you should replace this with dynamic user ID

        int rowsUpdated = database.update("User", values, whereClause, whereArgs);
        if (rowsUpdated > 0) {
            Toast.makeText(this, "PIN code updated successfully.", Toast.LENGTH_SHORT).show();
            navigateToPrecodeActivity();
        } else {
            Toast.makeText(this, "Failed to update PIN code.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navigates to the PrecodeActivity.
     */
    private void navigateToPrecodeActivity() {
        Intent intent = new Intent(this, precode.class);
        startActivity(intent);
        finish();
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

        public DatabaseHelper(android.content.Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
