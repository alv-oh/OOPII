# Movies Module — JavaFX Lab 2

## Overview
This is the Movies section of a JavaFX Movie Rental System built in pure Java (no FXML).
It allows users to add movies under specific genres and remove them from the system.

---

## Project Structure

```
src/com/example/
│
├── MoviesApp.java       → Entry point. Launches the JavaFX window.
├── MoviesView.java      → Builds the entire UI in Java. Handles all button logic.
├── GenreStore.java      → In-memory data store. Holds all genres and their movies.
└── Movie.java           → Model class. Represents a single movie (name + genre).
```

---

## Files Explained

### `Movie.java`
A simple model (blueprint) for a movie object.
Every movie has two fields:
- `name` — the title of the movie
- `genre` — the genre it belongs to

The `toString()` method returns just the name, so ComboBoxes display it cleanly.

---

### `GenreStore.java`
The in-memory database for the entire app.
Uses a **Singleton pattern** — meaning only one instance ever exists, shared across all parts of the app.

Internally it holds a `Map<String, List<Movie>>`:
- Keys are genre names (Action, Comedy, Horror, Romance, Sci-Fi)
- Values are lists of movies registered under that genre

**Methods:**
| Method | What it does |
|---|---|
| `getInstance()` | Returns the single shared instance |
| `addMovie(genre, name)` | Adds a movie to a genre. Rejects blanks and duplicates |
| `removeMovie(genre, name)` | Removes a movie from a genre |
| `getMovies(genre)` | Returns all movies in a given genre |

---

### `MoviesView.java`
The UI class. Extends `VBox` (a vertical layout container).
Builds and manages the entire interface programmatically — no FXML file needed.

**UI Components:**
| Component | Purpose |
|---|---|
| `genreComboBox` | Dropdown to select a genre |
| `movieNameField` | Text input for the movie name |
| `saveButton` | Saves the movie to the selected genre |
| `registeredComboBox` | Shows all movies in the selected genre |
| `removeButton` | Removes the selected movie |
| `saveStatusLabel` | Feedback message for save action |
| `removeStatusLabel` | Feedback message for remove action |

**Key behaviour:**
- Selecting a genre automatically refreshes the Registered dropdown
- Save validates that a genre is selected and the name is not empty or duplicate
- Remove validates that a genre and a movie are both selected
- Status labels turn green on success, red on error

---

### `MoviesApp.java`
The launch file. Extends JavaFX's `Application` class.
Creates a `MoviesView`, wraps it in a `Scene`, places it on the `Stage` (window), and displays it.

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
MoviesApp  →  launches
  MoviesView  →  builds UI, handles events
    GenreStore  →  reads and writes data
      Movie  →  the data itself
```

---

## Notes
- Data is stored **in memory only** — it resets every time the app is closed
- The Singleton pattern in `GenreStore` means if you later add Customers or Rentals modules,
  they can all share the same movie data without passing objects around
- No FXML or external layout files are used — the entire UI is built in Java code