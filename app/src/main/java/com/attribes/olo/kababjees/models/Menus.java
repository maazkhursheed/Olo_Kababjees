package com.attribes.olo.kababjees.models;

/**
 * Created by Maaz on 5/25/2016.
 */
public class Menus {

    private String title, genre, year;

    public Menus() {
    }

    public Menus(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
