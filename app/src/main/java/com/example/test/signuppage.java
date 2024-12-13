package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signuppage extends AppCompatActivity {

    private SQLiteDatabase database;
    private EditText firstNameEditText, lastNameEditText, dobEditText, contactNumberEditText,
            emailEditText, passwordEditText, confirmPasswordEditText, locationEditText, fieldToStudyText;
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

        // Set button click listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
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


        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || contactNumber.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("First_Name", firstName);
        values.put("Last_Name", lastName);
        values.put("Date_Of_Birth", dob);
        values.put("Contact_Number", contactNumber);
        values.put("Email", email);
        values.put("Password", password);
        values.put("Location", location);


        long result = database.insert("User", null, values);

        if (result == -1) {
            Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signuppage.this, Terms.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}
