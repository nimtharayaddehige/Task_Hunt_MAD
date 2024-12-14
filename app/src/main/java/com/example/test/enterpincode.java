package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class enterpincode extends AppCompatActivity {

    private StringBuilder pinCode = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpincode);

        // Set up keypad buttons
        setupKeypad();

        // Back Button Logic
        ImageButton backButton = findViewById(R.id.imageButton4);
        backButton.setOnClickListener(v -> {
            // Navigate to Login Page
            Intent intent = new Intent(enterpincode.this, loginpage.class);
            startActivity(intent);
            finish(); // Close current activity
        });
    }

    private void setupKeypad() {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                if (pinCode.length() < 4) {
                    pinCode.append(button.getText().toString()); // Append digit
                    updatePinDisplay(); // Update UI

                    if (pinCode.length() == 4) {
                        validateAndNavigate(); // Validate PIN
                    }
                }
            });
        }
    }

    private void updatePinDisplay() {
        // Update PIN circles to reflect entered digits (optional UI enhancement)
    }

    private void validateAndNavigate() {
        String enteredPin = pinCode.toString();

        if (validatePinFromDatabase(enteredPin)) {
            // PIN is valid, navigate to Student Home
            Toast.makeText(this, "PIN validated successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(enterpincode.this, studentHome.class);
            startActivity(intent);
            finish(); // Close current activity
        } else {
            // Invalid PIN, show error and reset
            Toast.makeText(this, "Invalid PIN. Try again.", Toast.LENGTH_SHORT).show();
            pinCode.setLength(0); // Clear entered PIN
            updatePinDisplay(); // Reset UI
        }
    }

    private boolean validatePinFromDatabase(String pinCode) {
        SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(this, "TaskHunt_Database_SQLite", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String createUserTable = "CREATE TABLE IF NOT EXISTS User (" +
                        "User_ID INTEGER PRIMARY KEY, " +
                        "First_Name TEXT NOT NULL, " +
                        "Last_Name TEXT NOT NULL, " +
                        "Date_Of_Birth DATE, " +
                        "Contact_Number TEXT, " +
                        "Email TEXT UNIQUE, " +
                        "Password TEXT NOT NULL, " +
                        "Location TEXT, " +
                        "Pin_Code TEXT)";
                db.execSQL(createUserTable);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS User");
                onCreate(db);
            }
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Pin_Code = ?", new String[]{pinCode});

        boolean isValid = cursor.getCount() > 0; // True if at least one row is found.
        cursor.close();
        db.close();

        return isValid;
    }
}
