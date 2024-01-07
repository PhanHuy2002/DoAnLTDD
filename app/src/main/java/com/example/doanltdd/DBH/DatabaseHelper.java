package com.example.doanltdd.DBH;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanltdd.model.User;
import com.example.doanltdd.model.Church;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    public static final String DATABASE_NAME = "ChurchApp.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USER = "user";
    public static final String TABLE_CHURCH = "church";

    // User table columns
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_USER_ADMIN = "user_admin";

    // Church table columns
    public static final String COLUMN_CHURCH_ID = "church_id";
    public static final String COLUMN_CHURCH_NAME = "church_name";
    public static final String COLUMN_CHURCH_ADDRESS = "church_address";
    public static final String COLUMN_CHURCH_SCHEDULE = "church_schedule";

    // Create user table statement
    public static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_ADMIN + " INTEGER)";

    // Create church table statement
    public static final String CREATE_CHURCH_TABLE = "CREATE TABLE " + TABLE_CHURCH + "("
            + COLUMN_CHURCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CHURCH_NAME + " TEXT,"
            + COLUMN_CHURCH_ADDRESS + " TEXT,"
            + COLUMN_CHURCH_SCHEDULE + " TEXT)";

    // Drop user table statement
    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // Drop church table statement
    public static final String DROP_CHURCH_TABLE = "DROP TABLE IF EXISTS " + TABLE_CHURCH;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CHURCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CHURCH_TABLE);
        // Create tables again
        onCreate(db);
    }

    // Add a user to the user table
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ADMIN, user.isAdmin() ? 1 : 0);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // Add a church to the church table
    public void addChurch(Church church) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHURCH_NAME, church.getName());
        values.put(COLUMN_CHURCH_ADDRESS, church.getAddress());
        values.put(COLUMN_CHURCH_SCHEDULE, church.getSchedule());
        db.insert(TABLE_CHURCH, null, values);
        db.close();
    }

    // Update a user in the user table
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ADMIN, user.isAdmin() ? 1 : 0);
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Update a church in the church table
    public void updateChurch(Church church) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHURCH_NAME, church.getName());
        values.put(COLUMN_CHURCH_ADDRESS, church.getAddress());
        values.put(COLUMN_CHURCH_SCHEDULE, church.getSchedule());
        db.update(TABLE_CHURCH, values, COLUMN_CHURCH_ID + " = ?", new String[]{String.valueOf(church.getId())});
        db.close();
    }

    // Delete a user from the user table
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Delete a church from the church table
    public void deleteChurch(Church church) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHURCH, COLUMN_CHURCH_ID + " = ?", new String[]{String.valueOf(church.getId())});
        db.close();
    }

    // Get a user by username and password from the user table
// @SuppressLint("Range")
    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_PASSWORD, COLUMN_USER_ADMIN},
                COLUMN_USER_NAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ADMIN)) == 1);
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            cursor.close();
            return user;
        }
        return null;
    }

    // Get a list of all users from the user table
//  @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ADMIN)) == 1);
                // Set the user id from the cursor data
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                // Add the user to the list
                users.add(user);
            } while (cursor.moveToNext()); // Move to the next row in the cursor
            // Close the cursor
            cursor.close();
        }
        // Return the list of users
        return users;
    }
}