package com.example.notes;

public class Note {
    private String title;
    private String description;
    private String dayOfWeek;
    private int priorty;

    public Note(String title, String description, String dayOfWeek, int priorty) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priorty = priorty;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriorty() {
        return priorty;
    }
}
