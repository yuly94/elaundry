package com.yuly.elaundry.kurir.model.geterseter;

/**
 * Created by anonymous on 25/11/16.
 */

public class Movie {
    private String title, genre, year;
    private int date;

    public Movie() {
    }

    public Movie(String title, String genre, String year, int date) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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