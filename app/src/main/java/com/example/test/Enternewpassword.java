package com.example.test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Enternewpassword extends AppCompatActivity {

    // Declare UI elements
    private EditText newPasswordField, reEnterPasswordField;
    private Button resetButton;
    private ImageButton backImageButton;

    // Database variables
    private SQLiteDatabase database;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enternewpassword);

        // Initialize UI elements
        newPasswordField = findViewById(R.id.editTextNumberPassword);
        reEnterPasswordField = findViewById(R.id.editTextNumberPassword2);
        resetButton = findViewById(R.id.sendCodeButton);
        backImageButton = findViewById(R.id.imageButton3);

        // Open database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        // Get the email passed from the ForgetPassword screen
        userEmail = getIntent().getStringExtra("EMAIL");

        // Set up button click listeners
        resetButton.setOnClickListener(v -> resetPassword());
        backImageButton.setOnClickListener(v -> navigateToForgetPassword());
    }

    private void resetPassword() {
        // Get the input from the fields
        String newPassword = newPasswordField.getText().toString().trim();
        String reEnterPassword = reEnterPasswordField.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Please enter a new password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(reEnterPassword)) {
            Toast.makeText(this, "Please re-enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(reEnterPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email exists in the database
        Cursor cursor = database.query("User", new String[]{"Email"}, "Email = ?", new String[]{userEmail}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Update the password in the database
            ContentValues values = new ContentValues();
            values.put("Password", newPassword);

            int rowsAffected = database.update("User", values, "Email = ?", new String[]{userEmail});
            if (rowsAffected > 0) {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                navigateToPasswordSuccessfully();
                finish(); // Navigate back to the previous screen
            } else {
                Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void navigateToForgetPassword() {
        // Navigate back to the ForgetPassword class
        Intent intent = new Intent(this, forgetpasswod.class);
        startActivity(intent);
        finish();
    }

    private void navigateToPasswordSuccessfully() {
        // Navigate back to the PasswordSuccessfully class
        Intent intent = new Intent(this, passwordsuccessful.class);
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
}

// DatabaseHelper class to manage SQLite database
class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(android.content.Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        db.execSQL("CREATE TABLE IF NOT EXISTS User (" +
                "User_ID INTEGER PRIMARY KEY, " +
                "First_Name TEXT NOT NULL, " +
                "Last_Name TEXT NOT NULL, " +
                "Date_Of_Birth DATE, " +
                "Contact_Number TEXT, " +
                "Email TEXT UNIQUE, " +
                "Password TEXT NOT NULL, " +
                "Location TEXT, " +
                "Pin_Code TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}
