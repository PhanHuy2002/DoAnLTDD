package com.example.doanltdd.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanltdd.DBH.DatabaseHelper;
import com.example.doanltdd.MainActivity;
import com.example.doanltdd.model.User;

public class LoginActivity extends AppCompatActivity {

    // Declare the views
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    // Declare the database helper
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the views
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set the click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password from the edit texts
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate the input
                if (username.isEmpty() || password.isEmpty()) {
                    // Show a toast message if the input is empty
                    Toast.makeText(LoginActivity.this, "Please enter your username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the user exists in the database
                    User user = databaseHelper.getUser(username, password);
                    if (user != null) {
                        // Show a toast message if the login is successful
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Create an intent to start the main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // Pass the user object to the main activity
                        intent.putExtra("user", user);
                        // Start the main activity
                        startActivity(intent);
                        // Finish the login activity
                        finish();
                    } else {
                        // Show a toast message if the login is failed
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set the click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the register activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // Start the register activity
                startActivity(intent);
            }
        });
    }
}
