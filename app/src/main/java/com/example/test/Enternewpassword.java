package com.example.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Enternewpassword extends AppCompatActivity {

    // Declare UI elements
    private EditText newPasswordField, reEnterPasswordField;
    private Button sendCodeButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enternewpassword);

        // Enable edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        newPasswordField = findViewById(R.id.newPasswordTextfield);
        reEnterPasswordField = findViewById(R.id.ReEnterPasswordTextfield);
        sendCodeButton = findViewById(R.id.sendCodeButton);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Set up button click listener
        sendCodeButton.setOnClickListener(v -> resetPassword());
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

        // Reset password using Firebase Authentication
        firebaseAuth.getCurrentUser().updatePassword(newPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to the previous screen
                    } else {
                        Toast.makeText(this, "Failed to reset password: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
