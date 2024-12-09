package com.example.openingactivity;

public class Event {
    private String name;
    private String host;
    private String location;
    private String date;

    public Event(String name, String host, String location, String date) {
        this.name = name;
        this.host = host;
        this.location = location;
        this.date = date;
    }

    // Getters and setters for the event attributes
    public String getName() {
        return name;
    }
    public String getHost() {
        return host;
    }
    public String getLocation() {
        return location;
        }
    public String getDate() {
        return date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
