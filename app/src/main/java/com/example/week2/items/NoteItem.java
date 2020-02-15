package com.example.week2.items;

public class NoteItem {

    private String title;
    private String description;
    private String color;
    private boolean significance;

    private int id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSignificance(boolean significance) {
        this.significance = significance;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public boolean isSignificance() {
        return significance;
    }
    public int getSignificance(){
        if(significance)
            return 1;
        else return 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
