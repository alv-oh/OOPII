package com.example;

import java.io.*;
import java.util.*;

public class GenreStore {

    private static GenreStore instance;

    public static final List<String> GENRES = List.of("Action", "Comedy", "Horror", "Romance", "Sci-Fi");

    private final Map<String, List<Movie>> data = new LinkedHashMap<>();

    // The file where movies are saved
    private static final String FILE_PATH = "movies.txt";

    private GenreStore() {
        for (String g : GENRES) {
            data.put(g, new ArrayList<>());
        }
    }

    public static GenreStore getInstance() {
        if (instance == null) {
            instance = new GenreStore();
        }
        return instance;
    }

    public boolean addMovie(String genre, String movieName) {
        if (movieName == null || movieName.isBlank()) return false;

        List<Movie> list = data.get(genre);
        if (list == null) return false;

        for (Movie m : list) {
            if (m.getName().equalsIgnoreCase(movieName.trim())) return false;
        }

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

    // Writes all movies to movies.txt
    // Each line is: genre,movieName
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String genre : GENRES) {
                for (Movie movie : data.get(genre)) {
                    writer.write(genre + "," + movie.getName());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Reads movies.txt and loads each line back into the store
    public void loadFromFile() {
        File file = new File(FILE_PATH);

        // If the file doesn't exist yet, nothing to load
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2); // split into exactly 2 parts
                if (parts.length == 2) {
                    String genre     = parts[0].trim();
                    String movieName = parts[1].trim();
                    addMovie(genre, movieName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
