package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
<<<<<<< Updated upstream
=======
import android.text.TextUtils;
>>>>>>> Stashed changes
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signuppage extends AppCompatActivity {

    private SQLiteDatabase database;
    private EditText firstNameEditText, lastNameEditText, dobEditText, contactNumberEditText,
<<<<<<< Updated upstream
            emailEditText, passwordEditText, confirmPasswordEditText, locationEditText, fieldToStudyText;
=======
            emailEditText, passwordEditText, confirmPasswordEditText, locationEditText;
>>>>>>> Stashed changes
    private Button nextButton, backButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastname);
        dobEditText = findViewById(R.id.birthday);
        contactNumberEditText = findViewById(R.id.contactNumber);
        emailEditText = findViewById(R.id.emailAddress);
        passwordEditText = findViewById(R.id.newPasswordTextfield);
        confirmPasswordEditText = findViewById(R.id.repassword);
        locationEditText = findViewById(R.id.location);
        backButton = findViewById(R.id.backbtn);
        nextButton = findViewById(R.id.nextbtn);

        // Open or create the database
        database = openOrCreateDatabase("TaskHunt_Database_SQLite", MODE_PRIVATE, null);

<<<<<<< Updated upstream
        // Set button click listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
=======
        // Set listeners for buttons
        nextButton.setOnClickListener(v -> registerUser());
        backButton.setOnClickListener(v -> navigateToEnterApp());
>>>>>>> Stashed changes
    }

    private void registerUser() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String contactNumber = contactNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

<<<<<<< Updated upstream

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || contactNumber.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
=======
        // Validate input fields
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(dob) ||
                TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
>>>>>>> Stashed changes
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

<<<<<<< Updated upstream
=======
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare values for insertion
>>>>>>> Stashed changes
        ContentValues values = new ContentValues();
        values.put("First_Name", firstName);
        values.put("Last_Name", lastName);
        values.put("Date_Of_Birth", dob);
        values.put("Contact_Number", contactNumber);
        values.put("Email", email);
        values.put("Password", password);
        values.put("Location", location);

<<<<<<< Updated upstream

=======
        // Insert into the database
>>>>>>> Stashed changes
        long result = database.insert("User", null, values);

        if (result == -1) {
            Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
<<<<<<< Updated upstream
=======
            // Navigate to Terms class
>>>>>>> Stashed changes
            Intent intent = new Intent(signuppage.this, Terms.class);
            startActivity(intent);
            finish();
        }
    }

<<<<<<< Updated upstream
=======
    private void navigateToEnterApp() {
        // Navigate to EnterApp class
        Intent intent = new Intent(signuppage.this, enterapp.class);
        startActivity(intent);
        finish();
    }

>>>>>>> Stashed changes
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}
