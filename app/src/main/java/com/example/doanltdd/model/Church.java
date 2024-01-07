package com.example.doanltdd.model;

// Church.java
import java.io.Serializable;

public class Church implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Church(String name, String address, String schedule) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.schedule = schedule;
    }
    public Church(){
    }
    // Declare the fields
    private int id;
    private String name;
    private String address;
    private String schedule;
}