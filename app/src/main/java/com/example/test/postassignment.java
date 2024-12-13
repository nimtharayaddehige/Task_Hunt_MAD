package com.example.test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class postassignment extends AppCompatActivity {

    private EditText assignmentTitle;
    private EditText description;
    private EditText subjectArea;
    private EditText dueDate;
    private EditText dueTime;
    private EditText paymentOffer;
    private Button submitButton;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_postassignment);

        // Initialize UI elements
        assignmentTitle = findViewById(R.id.assignmentTitle);
        description = findViewById(R.id.description);
        subjectArea = findViewById(R.id.subjectArea);
        dueDate = findViewById(R.id.dueDate);
        dueTime = findViewById(R.id.dueTime);
        paymentOffer = findViewById(R.id.paymentOffer);
        submitButton = findViewById(R.id.submitButton);

        // Initialize database
        AssignmentDatabaseHelper dbHelper = new AssignmentDatabaseHelper(this, "TaskHunt_Database_SQLite", null, 1);
        database = dbHelper.getWritableDatabase();

        // Set up edge-to-edge display
<<<<<<< Updated upstream
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
=======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ResetButton), (v, insets) -> {
>>>>>>> Stashed changes
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up Submit button click listener
        submitButton.setOnClickListener(v -> handleSubmitAssignment());
    }

    /**
     * Handles the assignment submission logic.
     */
    private void handleSubmitAssignment() {
        String title = assignmentTitle.getText().toString().trim();
        String desc = description.getText().toString().trim();
        String subject = subjectArea.getText().toString().trim();
        String date = dueDate.getText().toString().trim();
        String time = dueTime.getText().toString().trim();
        String payment = paymentOffer.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || subject.isEmpty() || date.isEmpty() || time.isEmpty() || payment.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
        } else {
            // Insert assignment into the database
            ContentValues values = new ContentValues();
            values.put("Title", title);
            values.put("Description", desc);
            values.put("SubjectArea", subject);
            values.put("DueDate", date);
            values.put("DueTime", time);
            values.put("PaymentOffer", payment);

            long newRowId = database.insert("Assignments", null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();

                // Clear input fields after submission
                assignmentTitle.setText("");
                description.setText("");
                subjectArea.setText("");
                dueDate.setText("");
                dueTime.setText("");
                paymentOffer.setText("");
            } else {
                Toast.makeText(this, "Failed to submit assignment. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }

    /**
     * SQLiteOpenHelper for managing the database.
     */
    private static class AssignmentDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TaskHunt_Database_SQLite";
        private static final int DATABASE_VERSION = 1;

        // SQL command to create the Assignments table
        private static final String CREATE_ASSIGNMENTS_TABLE =
                "CREATE TABLE Assignments (" +
                        "Assignment_ID INTEGER PRIMARY KEY, " +
                        "Title TEXT, " +
                        "Description TEXT, " +
                        "SubjectArea TEXT, " +
                        "DueDate TEXT, " +
                        "DueTime TEXT, " +
                        "PaymentOffer TEXT);";

        public AssignmentDatabaseHelper(android.content.Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ASSIGNMENTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Assignments");
            onCreate(db);
        }
    }
}
