package com.example.doanltdd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doanltdd.DBH.DatabaseHelper;
import com.example.doanltdd.model.Church;
import com.example.doanltdd.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declare the views
    private TextView welcomeTextView;
    private EditText nameEditText;
    private EditText timeEditText;
    private Button searchButton;
    private Button manageButton;
    private Button scanButton;
    private ListView churchListView;

    // Declare the database helper
    private DatabaseHelper databaseHelper;

    // Declare the user object
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        welcomeTextView = findViewById(R.id.welcome_text_view);
        nameEditText = findViewById(R.id.name_edit_text);
        timeEditText = findViewById(R.id.time_edit_text);
        searchButton = findViewById(R.id.search_button);
        manageButton = findViewById(R.id.manage_button);
        scanButton = findViewById(R.id.scan_button);
        churchListView = findViewById(R.id.church_list_view);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Get the user object from the intent
        user = (User) getIntent().getSerializableExtra("user");

        // Set the welcome text view with the user's username
        welcomeTextView.setText("Welcome, " + user.getUsername() + "!");

        // Set the visibility of the manage button depending on the user's admin status
        if (user.isAdmin()) {
            manageButton.setVisibility(View.VISIBLE);
        } else {
            manageButton.setVisibility(View.GONE);
        }

        // Set the click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the name and time from the edit texts
                String name = nameEditText.getText().toString();
                String time = timeEditText.getText().toString();

                // Validate the input
                if (name.isEmpty() && time.isEmpty()) {
                    // Show all churches if the input is empty
                    List<Church> churches = databaseHelper.getAllChurches();
                    // Create an adapter to display the churches in the list view
                    ChurchAdapter adapter = new ChurchAdapter(MainActivity.this, churches);
                    // Set the adapter to the list view
                    churchListView.setAdapter(adapter);
                } else {
                    // Search for churches by name and/or time
                    List<Church> churches = databaseHelper.searchChurches(name, time);
                    // Create an adapter to display the churches in the list view
                    ChurchAdapter adapter = new ChurchAdapter(MainActivity.this, churches);
                    // Set the adapter to the list view
                    churchListView.setAdapter(adapter);
                }
            }
        });

        // Set the click listener for the manage button
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the manage activity
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                // Pass the user object to the manage activity
                intent.putExtra("user", user);
                // Start the manage activity
                startActivity(intent);
            }
        });

        // Set the click listener for the scan button
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the scan activity
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                // Start the scan activity
                startActivity(intent);
            }
        });

        // Set the item click listener for the list view
        churchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the church object from the adapter
                Church church = (Church) churchListView.getAdapter().getItem(position);
                // Create an intent to start the detail activity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                // Pass the church object to the detail activity
                intent.putExtra("church", church);
                // Start the detail activity
                startActivity(intent);
            }
        });
    }
}
