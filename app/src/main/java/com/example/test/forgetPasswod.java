package com.example.test;

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

import java.util.Random;

public class forgetpasswod extends AppCompatActivity {

    private EditText emailField;
    private Button sendCodeButton;
    private ImageButton backImageButton;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpasswod);

        // Enable edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ResetButton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        emailField = findViewById(R.id.emailTextField);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        backImageButton = findViewById(R.id.backImageButton);

        // Initialize SQLite database helper
        dbHelper = new UserDatabaseHelper(this);

        // Set button click listeners
        sendCodeButton.setOnClickListener(v -> sendResetCode());
        backImageButton.setOnClickListener(v -> navigateToLoginPage());
    }

    private void navigateToLoginPage() {
        Intent intent = new Intent(forgetpasswod.this, loginpage.class);
        startActivity(intent);
        finish();
    }

    private void sendResetCode() {
        String email = emailField.getText().toString().trim();

        // Validate email input
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email exists in the database
        if (dbHelper.isEmailExists(email)) {
            String otp = generateOTP();
            sendOTP(email, otp);
            Toast.makeText(this, "OTP sent to: " + email, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "INCORRECT EMAIL OR THIS EMAIL IS NOT FOUND IN OUR DATABASE", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private void sendOTP(String email, String otp) {
        // Simulate sending OTP - Replace with actual email API integration
        System.out.println("Sending OTP: " + otp + " to email: " + email);
        // Navigate to the enterpincode activity
        Intent intent = new Intent(this, Enternewpassword.class);
        startActivity(intent);
        finish(); // Close current activity
    }

    // SQLite Database Helper Class
    static class UserDatabaseHelper extends draft.TaskDatabaseHelper {

        public UserDatabaseHelper(forgetpasswod context) {
            super(context);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("CREATE TABLE IF NOT EXISTS User (" +
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
    }
}
