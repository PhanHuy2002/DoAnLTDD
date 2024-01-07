package com.example.doanltdd.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanltdd.DBH.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    // Declare the views
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;

    // Declare the database helper
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the views
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        registerButton = findViewById(R.id.register_button);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set the click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username, password, and confirm password from the edit texts
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Validate the input
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    // Show a toast message if the input is empty
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    // Show a toast message if the password and confirm password do not match
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the username already exists in the database
                    User user = databaseHelper.getUser(username, password);
                    if (user != null) {
                        // Show a toast message if the username is taken
                        Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Create a new user object with the input data
                        user = new User(username, password, false);
                        // Add the user to the database
                        databaseHelper.addUser(user);
                        // Show a toast message if the registration is successful
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        // Finish the register activity
                        finish();
                    }
                }
            }
        });
    }
}
