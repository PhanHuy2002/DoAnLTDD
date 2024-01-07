package com.example.doanltdd.model;

// User.java
import java.io.Serializable;

public class User implements Serializable {
    // Declare the fields
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    // Declare the constructor
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    public User(){
    }
    // Declare the getters and setters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

