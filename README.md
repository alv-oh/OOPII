# Movies Module — JavaFX Lab 2

## Overview
The Movies section of a JavaFX Movie Rental System, built in pure Java (no FXML).
Allows users to add movies under specific genres and remove them from the system.

---

## Project Structure

```
src/com/example/
│
├── MoviesApp.java      → Entry point. Launches the JavaFX window.
├── MoviesView.java     → Builds the UI in Java. Handles all button logic.
├── GenreStore.java     → In-memory store. Holds all genres and their movies.
└── Movie.java          → Model class. Represents a single movie (name + genre).
```

---

## Files Explained

### `Movie.java`
A blueprint for a movie object. Every movie has a `name` and a `genre`.
The `toString()` method returns just the name so ComboBoxes display it cleanly.

### `GenreStore.java`
The in-memory database. Uses a **Singleton pattern** so only one instance exists
and is shared across the whole app.

Stores data as `Map<String, List<Movie>>` — each genre maps to a list of its movies:
```
"Action"  → [Inception, John Wick]
"Comedy"  → [Home Alone]
"Horror"  → []
```

| Method | What it does |
|---|---|
| `getInstance()` | Returns the single shared instance |
| `addMovie(genre, name)` | Adds a movie. Rejects blanks and duplicates |
| `removeMovie(genre, name)` | Removes a movie from a genre |
| `getMovies(genre)` | Returns all movies in a given genre |

### `MoviesView.java`
The UI class. Extends `VBox` (vertical layout — everything stacks top to bottom).
Builds the interface in Java code and wires all button actions.

- Selecting a genre refreshes the Registered dropdown automatically
- Save validates input then calls `store.addMovie()`
- Remove validates selection then calls `store.removeMovie()`
- Status labels show feedback after each action

### `MoviesApp.java`
The launch file. Creates a `MoviesView`, wraps it in a `Scene`, puts it on the
`Stage` (window) and calls `show()`.

---

## How to Run in IntelliJ

1. Place all 4 files in `src/com/example/`
2. Go to **Run → Edit Configurations → VM Options** and add:
   ```
   --module-path "C:\java\javafx-sdk-25.0.3\lib" --add-modules javafx.controls,javafx.fxml
   ```
3. Set the main class to `com.example.MoviesApp`
4. Click Run

---

## Data Flow

```
MoviesApp     → launches the window
  MoviesView  → builds UI, handles button clicks
    GenreStore → reads and writes movie data
      Movie   → the data itself
```

---

## Notes
- Data is **in memory only** — resets when the app closes
- The Singleton in `GenreStore` means future modules (Customers, Rentals)
  can share the same data without passing objects around
- No FXML — the entire UI is built directly in Java