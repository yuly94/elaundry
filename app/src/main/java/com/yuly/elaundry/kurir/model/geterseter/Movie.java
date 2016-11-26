package com.yuly.elaundry.kurir.model.geterseter;

/**
 * Created by anonymous on 25/11/16.
 */

public class Movie {
    private String title, genre, year, nomer;
    private int noid;

    public Movie() {
    }

    public Movie(String title, String genre, String year, int noid, String nomer) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.noid = noid;
        this.nomer = nomer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getNoId() {
        return noid;
    }

    public void setNoId(int noid) {
        this.noid = noid;
    }


    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
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