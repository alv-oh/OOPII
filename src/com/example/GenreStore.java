package com.example;

import java.util.*;

public class GenreStore {

    private static GenreStore instance;

    public static final List<String> GENRES =
            List.of("Action", "Comedy", "Horror", "Romance", "Sci-Fi");

    private final Map<String, List<Movie>> data = new LinkedHashMap<>();
//    This is the actual storage — a Map where each genre name points to a list of movies.

    private GenreStore() {
        for (String g : GENRES) data.put(g, new ArrayList<>());
    }

    public static GenreStore getInstance() {
        if (instance == null) instance = new GenreStore();
        return instance;
    }

    public boolean addMovie(String genre, String movieName) {
        if (movieName == null || movieName.isBlank()) return false;
        List<Movie> list = data.get(genre);
        if (list == null) return false;
        for (Movie m : list)
            if (m.getName().equalsIgnoreCase(movieName.trim())) return false;
        list.add(new Movie(movieName.trim(), genre));
        return true;
    }

    public boolean removeMovie(String genre, String movieName) {
        List<Movie> list = data.get(genre);
        if (list == null) return false;
        return list.removeIf(m -> m.getName().equals(movieName));
    }

    public List<Movie> getMovies(String genre) {
        return data.getOrDefault(genre, Collections.emptyList());
    }
}