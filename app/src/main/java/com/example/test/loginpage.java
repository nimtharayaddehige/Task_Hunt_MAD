package com.example.test;

import android.content.Intent;
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
        emailField = findViewById(R.id.emailEditText);
        passwordField = findViewById(R.id.editTextTextPassword3);
        loginButton = findViewById(R.id.loginButton);
        googleSignInButton = findViewById(R.id.googleSignInButton);
        forgotPassword = findViewById(R.id.forgotPassword1);
        signUpLink = findViewById(R.id.forgotPassword2);

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

        // Validate user credentials in SQLite database
        if (dbHelper.validateUser(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Navigate to the next activity
            startActivity(new Intent(this, manageassignment.class));
            finish(); // Close the login page
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
        startActivity(new Intent(this, forgetpasswod.class));
    }

    // Navigate to sign-up page
    private void navigateToSignUp() {
        startActivity(new Intent(this, signuppage.class));
    }
}
