package com.example.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class forgetPasswod extends AppCompatActivity {

    // Declare UI elements
    private EditText emailField, passwordField;
    private Button sendCodeButton, loginButton;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpasswod);

        // Enable edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        emailField = findViewById(R.id.emailField); // ID for the email input field
        sendCodeButton = findViewById(R.id.sendCodeButton); // ID for the "Send Code" button

        // Initialize SQLite database helper
        dbHelper = new UserDatabaseHelper(this);

        // Set button click listeners
        sendCodeButton.setOnClickListener(v -> resetPassword());
        loginButton.setOnClickListener(v -> validateLogin());
    }

    private void resetPassword() {
        String email = emailField.getText().toString().trim();

        // Validate email input
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email exists in the database
        if (dbHelper.isEmailExists(email)) {
            Toast.makeText(this, "Password reset instructions sent to: " + email, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Email not found in the system", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateLogin() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check credentials in the database
        if (dbHelper.validateUser(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    // SQLite Database Helper Class
    static class UserDatabaseHelper extends draft.TaskDatabaseHelper {

        public UserDatabaseHelper(forgetPasswod context) {
            super(context);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("CREATE TABLE User (" +
                    "User_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "First_Name TEXT NOT NULL," +
                    "Last_Name TEXT NOT NULL," +
                    "Date_Of_Birth DATE," +
                    "Contact_Number TEXT," +
                    "Email TEXT UNIQUE," +
                    "Password TEXT NOT NULL," +
                    "Location TEXT," +
                    "Pin_Code TEXT)");
        }

        public boolean isEmailExists(String email) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Email = ?", new String[]{email});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }

        public boolean validateUser(String email, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Email = ? AND Password = ?", new String[]{email, password});
            boolean isValid = cursor.getCount() > 0;
            cursor.close();
            return isValid;
        }
    }
}
