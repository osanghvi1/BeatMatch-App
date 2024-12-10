package com.example.openingactivity;

import android.graphics.drawable.Drawable;

public class Event {
    private Drawable thumbnail;
    private String name;
    private String host;
    private String location;
    private String date;

    public Event(String name, String host, String location, String date, Drawable thumbnail) {
        this.name = name;
        this.host = host;
        this.location = location;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    // Getters and setters for the event attributes
    public String getEventName() {
        return name;
    }
    public String getEventHost() {
        return host;
    }
    public String getEventLocation() {
        return location;
        }
    public String getEventDate() {
        return date;
    }
    public void setEventName(String name) {
        this.name = name;
    }
    public void setEventHost(String host) {
        this.host = host;
    }
    public void setEventLocation(String location) {
        this.location = location;
    }
    public void setEventDate(String date) {
        this.date = date;
    }
    public Drawable getEventThumbnail() {
        return thumbnail;
    }
    public void setEventThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }
}
