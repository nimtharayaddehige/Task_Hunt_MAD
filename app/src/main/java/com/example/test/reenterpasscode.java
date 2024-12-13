package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class reenterpasscode extends AppCompatActivity {

    private SQLiteDatabase database;
    private EditText newPinCodeInput;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reenterpasscode);

        applyEdgeToEdgeUI();
        initializeUIComponents();
        initializeDatabase();

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
        resetButton = findViewById(R.id.resetButtonAll);
    }

    /**
     * Initializes the database connection.
     */
    private void initializeDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Handles reset button click.
     */
    private void handleResetButtonClick() {
        String newPinCode = newPinCodeInput.getText().toString().trim();
        if (!newPinCode.isEmpty()) {
            updatePinCode(newPinCode);
        } else {
            Toast.makeText(this, "Please enter a valid PIN code.", Toast.LENGTH_SHORT).show();
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

        int rowsUpdated = database.update("User", values, null, null); // Update all rows for simplicity
        if (rowsUpdated > 0) {
            Toast.makeText(this, "PIN code updated successfully.", Toast.LENGTH_SHORT).show();
            navigateToPrecodeActivity();
        } else {
            Toast.makeText(this, "Failed to update PIN code.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navigates to the precode activity.
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
