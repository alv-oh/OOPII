package com.example;

public class Movie {
    private String name;
    private String genre;

    public Movie(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName()  { return name; }
    public String getGenre() { return genre; }

    @Override
    public String toString() { return name; }
}