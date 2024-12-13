package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginpage extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton, googleSignInButton;
    private TextView forgotPassword, signUpLink;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        // Initialize views
        emailField = findViewById(R.id.emailTextField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        googleSignInButton = findViewById(R.id.googleSignInButton);
        forgotPassword = findViewById(R.id.forgotPassword1);
        signUpLink = findViewById(R.id.signupField);

        // Initialize SQLite database helper
        dbHelper = new UserDatabaseHelper(this);

        // Set up click listeners
        loginButton.setOnClickListener(v -> handleLogin());
        googleSignInButton.setOnClickListener(v -> handleGoogleSignIn());
        forgotPassword.setOnClickListener(v -> navigateToForgotPassword());
        signUpLink.setOnClickListener(v -> navigateToSignUp());
    }

    // Handle login functionality
    private void handleLogin() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Validate user input
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return;
        }

<<<<<<< Updated upstream
        // Validate user credentials in SQLite database
        if (dbHelper.validateUser(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Navigate to the next activity
            startActivity(new Intent(this, manageassignment.class));
=======
        // Check credentials in the database
        if (dbHelper.validateUser(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Navigate to the enterpincode activity
            Intent intent = new Intent(this, enterpincode.class);
            startActivity(intent);
            finish(); // Close current activity
>>>>>>> Stashed changes
        } else {
            Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
        }
    }

    // Placeholder for Google Sign-In logic
    private void handleGoogleSignIn() {
        Toast.makeText(this, "Google Sign-In clicked.", Toast.LENGTH_SHORT).show();
        // Add your Google Sign-In logic here
    }

    // Navigate to forgot password page
    private void navigateToForgotPassword() {
<<<<<<< Updated upstream
        Toast.makeText(this, "Forgot Password clicked.", Toast.LENGTH_SHORT).show();
        // Navigate to forgot password page
        startActivity(new Intent(this, forgetPasswod.class));
=======
        Intent intent = new Intent(this, forgetPasswod.class);
        startActivity(intent);
>>>>>>> Stashed changes
    }

    // Navigate to sign-up page
    private void navigateToSignUp() {
<<<<<<< Updated upstream
        Toast.makeText(this, "Sign-Up clicked.", Toast.LENGTH_SHORT).show();
        // Navigate to sign-up page
        startActivity(new Intent(this, signuppage.class));
=======
        Intent intent = new Intent(this, signuppage.class);
        startActivity(intent);
>>>>>>> Stashed changes
    }

    // SQLite Database Helper Class
    static class UserDatabaseHelper extends draft.TaskDatabaseHelper {

        public UserDatabaseHelper(loginpage context) {
            super(context);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            super.onCreate(db);
<<<<<<< Updated upstream
            db.execSQL("CREATE TABLE User (" +
=======
            db.execSQL("CREATE TABLE IF NOT EXISTS User (" +
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
        public boolean validateUser(String email, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Email = ? AND Password = ?", new String[]{email, password});
            boolean isValid = cursor.getCount() > 0;
=======
        // Validate user credentials
        public boolean validateUser(String email, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Email = ? AND Password = ?", new String[]{email, password});
            boolean isValid = cursor.moveToFirst();
>>>>>>> Stashed changes
            cursor.close();
            return isValid;
        }
    }
}
