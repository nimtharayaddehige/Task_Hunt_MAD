package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity {

    private SQLiteDatabase database;
    private EditText firstNameEditText, lastNameEditText, dobEditText, contactNumberEditText,
            emailEditText, passwordEditText, confirmPasswordEditText, locationEditText;

    private Button nextButton, backButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        dobEditText = findViewById(R.id.birthday);
        contactNumberEditText = findViewById(R.id.contactNumber);
        emailEditText = findViewById(R.id.emailAddress);
        passwordEditText = findViewById(R.id.editTextNumberPassword);
        confirmPasswordEditText = findViewById(R.id.repassword);
        locationEditText = findViewById(R.id.location);
        backButton = findViewById(R.id.backbtn);
        nextButton = findViewById(R.id.nextbtn);

        // Open or create the database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

        // Set button click listeners
        nextButton.setOnClickListener(v -> registerUser());
        backButton.setOnClickListener(v -> navigateToEnterApp());
    }

    private void registerUser() {
        // Get user input
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String contactNumber = contactNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Validate inputs
        if (validateInputs(firstName, lastName, dob, contactNumber, email, password, confirmPassword)) {
            // Prepare values for insertion into the database
            ContentValues values = new ContentValues();
            values.put("First_Name", firstName);
            values.put("Last_Name", lastName);
            values.put("Date_Of_Birth", dob);
            values.put("Contact_Number", contactNumber);
            values.put("Email", email);
            values.put("Password", password);
            values.put("Location", location);

            // Insert into the database
            long result = database.insert("User", null, values);

            // Check the result and show appropriate message
            if (result == -1) {
                Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                // Navigate to Terms activity
                Intent intent = new Intent(SignUpPage.this, Terms.class);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * Validates user inputs.
     *
     * @param firstName       The first name entered by the user.
     * @param lastName        The last name entered by the user.
     * @param dob             The date of birth entered by the user.
     * @param contactNumber   The contact number entered by the user.
     * @param email           The email address entered by the user.
     * @param password        The password entered by the user.
     * @param confirmPassword The confirm password entered by the user.
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInputs(String firstName, String lastName, String dob, String contactNumber,
                                   String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(dob) ||
                TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void navigateToEnterApp() {
        // Navigate to EnterApp class
        Intent intent = new Intent(SignUpPage.this, enterapp.class);
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
