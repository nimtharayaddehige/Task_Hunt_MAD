package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
<<<<<<< Updated upstream
=======
import android.os.Handler;
import android.view.View;
>>>>>>> Stashed changes
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class reenterpasscode extends AppCompatActivity {

<<<<<<< Updated upstream
    private SQLiteDatabase database;
    private EditText newPinCodeInput;
    private Button resetButton;

=======
    private EditText reenterPinInput;
    private Button resetButton;
    private SQLiteDatabase database;

    @SuppressLint("MissingInflatedId")
>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reenterpasscode);

<<<<<<< Updated upstream
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
=======
        reenterPinInput = findViewById(R.id.reenterPinInput);
        resetButton = findViewById(R.id.resetButton);

        // Get the precode value from the intent
        String precode = getIntent().getStringExtra("precode");

        // Initialize database (replace with your database helper class)
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reenteredPin = reenterPinInput.getText().toString().trim();
                if (reenteredPin.length() == 4 && reenteredPin.equals(precode)) { // Validate re-entered PIN
                    updatePinCode(reenteredPin);
                } else {
                    Toast.makeText(reenterpasscode.this, "PIN codes do not match or are invalid.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePinCode(String newPinCode) {
        ContentValues values = new ContentValues();
        values.put("Pin_Code", newPinCode);

        // Replace 'User_ID' with the actual user identifier column
        String whereClause = "User_ID = ?";
        String[] whereArgs = new String[]{"1"}; // Replace '1' with the dynamic user ID

        int rowsUpdated = database.update("User", values, whereClause, whereArgs);
        if (rowsUpdated > 0) {
            Toast.makeText(this, "PIN updated successfully. Redirecting to login...", Toast.LENGTH_SHORT).show();
            navigateToLoginPage(); // Navigate to login page
        } else {
            Toast.makeText(this, "Failed to update PIN.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLoginPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(reenterpasscode.this, loginpage.class); // Replace with your actual login page class
                startActivity(intent);
                finish();
            }
        }, 2000); // Delay for 2 seconds to show the success message
>>>>>>> Stashed changes
    }
}
