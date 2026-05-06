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

## Data Flow

```
main() → launch() → start() → new MoviesView() → buildUI() + wireEvents()
                                      ↕
                                GenreStore (data)
                                      ↕
                                 Movie (objects)
```

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

## Code Explained Line by Line

---

### `Movie.java`

```java
package com.example;
```
Declares which package this file belongs to. Think of a package like a folder — it groups related classes together.

---

```java
public class Movie {
```
Defines a class called `Movie`. `public` means any other class in the project can use it.

---

```java
private String name;
private String genre;
```
The two fields every movie has — a name and a genre. `private` means only code inside this class can directly read or change them.

---

```java
public Movie(String name, String genre) {
    this.name = name;
    this.genre = genre;
}
```
The **constructor** — runs when you create a new movie with `new Movie("Inception", "Sci-Fi")`.
`this.name` refers to the field, `name` refers to the parameter being passed in.
Without `this`, Java would get confused between the two.

---

```java
public String getName()  { return name; }
public String getGenre() { return genre; }
```
**Getters** — the only way to read `name` and `genre` from outside this class since they are private.

---

```java
@Override
public String toString() { return name; }
```
Called automatically when Java needs to display this object as text — like inside a ComboBox dropdown.
We return just the name so it shows `"Inception"` instead of something like `com.example.Movie@5f4da5c3`.

---
---

### `GenreStore.java`

```java
package com.example;
```
This class belongs to the `com.example` package.

---

```java
import java.util.*;
```
Imports everything from `java.util` — gives us access to `List`, `Map`, `ArrayList`,
`LinkedHashMap`, and `Collections` without writing the full path each time.

---

```java
public class GenreStore {
```
Defines the class. This is the in-memory database for the whole app.

---

```java
private static GenreStore instance;
```
Holds the one and only copy of `GenreStore`. `static` means it belongs to the class itself,
not to any object. Starts as `null`.

---

```java
public static final List<String> GENRES = List.of("Action", "Comedy", "Horror", "Romance", "Sci-Fi");
```
A fixed list of genre names. `static final` means it is shared across the whole app and can never
be changed. `List.of()` creates an unmodifiable list.

---

```java
private final Map<String, List<Movie>> data = new LinkedHashMap<>();
```
The actual storage. A `Map` where each key is a genre name and each value is a list of movies
in that genre. `LinkedHashMap` keeps genres in the order they were inserted.

---

```java
private GenreStore() {
    for (String g : GENRES) {
        data.put(g, new ArrayList<>());
    }
}
```
The constructor is `private` — nobody outside can do `new GenreStore()`.
The loop goes through each genre and creates an empty movie list for it.
So at start you get `"Action" → []`, `"Comedy" → []`, and so on.

---

```java
public static GenreStore getInstance() {
    if (instance == null) {
        instance = new GenreStore();
    }
    return instance;
}
```
The only way to get a `GenreStore`. First time it is called, `instance` is null so it creates one.
Every call after that returns the same one. This is the **Singleton pattern** — only one store
ever exists, shared across the whole app.

---

```java
public boolean addMovie(String genre, String movieName) {
```
Takes a genre and a movie name, tries to add it, returns `true` if it succeeded or `false` if it failed.

---

```java
if (movieName == null || movieName.isBlank()) return false;
```
First validation — if the name is null or just whitespace, reject it immediately.

---

```java
List<Movie> list = data.get(genre);
if (list == null) return false;
```
Gets the movie list for that genre. If the genre does not exist in the map, `get()` returns null
and we reject it.

---

```java
for (Movie m : list) {
        if (m.getName().equalsIgnoreCase(movieName.trim())) return false;
        }
```
Loops through existing movies in that genre. If any one of them matches the new name
(ignoring case), it is a duplicate — reject it.

---

```java
list.add(new Movie(movieName.trim(), genre));
        return true;
```
No issues found — create a new `Movie` object and add it to the list.
`.trim()` removes any accidental spaces. Return `true` to signal success.

---

```java
public boolean removeMovie(String genre, String movieName) {
    List<Movie> list = data.get(genre);
    if (list == null) return false;
    return list.removeIf(m -> m.getName().equals(movieName));
}
```
Gets the list for that genre. `removeIf()` goes through the list and removes any movie whose
name matches. Returns `true` if something was removed, `false` if nothing matched.

---

```java
public List<Movie> getMovies(String genre) {
    return data.getOrDefault(genre, Collections.emptyList());
}
```
Returns all movies for a genre. `getOrDefault()` means — if that genre key exists return its list,
if not return an empty list instead of null. Safer than a plain `get()`.

---
---

### `MoviesView.java`

```java
package com.example;
```
Package declaration.

---

```java
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.stream.Collectors;
```
Imports everything the UI needs. `FXCollections` for populating dropdowns, `Insets` for padding,
`*` from controls gets all UI components (Label, Button, ComboBox etc.), `VBox` for the layout,
and the stream imports for converting lists.

---

```java
public class MoviesView extends VBox {
```
`MoviesView` **is** a `VBox` — a vertical box layout. Everything placed inside stacks top to bottom.
`extends` means it inherits all of VBox's behaviour.

---

```java
private final GenreStore store = GenreStore.getInstance();
```
Gets the single shared instance of `GenreStore`. Every time this view reads or writes data,
it goes through this.

---

```java
private final ComboBox<String> genreComboBox     = new ComboBox<>();
private final TextField        movieNameField     = new TextField();
private final Button           saveButton         = new Button("Save Movie");
private final Label            saveStatusLabel    = new Label();
private final ComboBox<String> registeredComboBox = new ComboBox<>();
private final Button           removeButton       = new Button("Remove Movie");
private final Label            removeStatusLabel  = new Label();
```
All UI controls declared as fields at the top of the class. They are fields (not local variables)
so both `buildUI()` and `wireEvents()` can access them. `final` means each variable can only
be assigned once.

---

```java
public MoviesView() {
    buildUI();
    wireEvents();
}
```
The constructor. When `new MoviesView()` is called it immediately builds the layout then wires
up the button actions. Two clean responsibilities, two separate methods.

---

```java
setSpacing(10);
setPadding(new Insets(20));
```
`setSpacing(10)` puts 10px of gap between each child in the VBox.
`setPadding(new Insets(20))` adds 20px of space around the inside edges of the layout.

---

```java
genreComboBox.setItems(FXCollections.observableArrayList(GenreStore.GENRES));
        genreComboBox.setPromptText("Select a genre");
```
Loads the 5 genres into the dropdown. `FXCollections.observableArrayList()` converts the plain
list into a JavaFX-friendly list the UI can watch for changes. `setPromptText` is the grey
placeholder text shown when nothing is selected.

---

```java
registeredComboBox.setPromptText("Select a movie");
```
Sets placeholder text. This dropdown starts empty — it only gets populated after a genre is selected.

---

```java
getChildren().addAll(
    new Label("Genre:"),      genreComboBox,
    new Label("Name:"),       movieNameField,
saveButton,
saveStatusLabel,
        new Separator(),
    new Label("Registered:"), registeredComboBox,
removeButton,
removeStatusLabel
);
```
`getChildren()` is the VBox's list of things to display. `addAll()` adds everything at once in order
from top to bottom. `new Separator()` draws a horizontal line between the two sections.

---

```java
genreComboBox.getSelectionModel().selectedItemProperty().addListener(
    (obs, oldVal, newVal) -> refreshRegistered(newVal)
);
```
Attaches a **listener** to the genre dropdown. Every time the selected item changes this fires
automatically. `obs` is the observable property, `oldVal` is what was selected before, `newVal`
is the newly selected genre. We pass `newVal` to `refreshRegistered()`.

---

```java
saveButton.setOnAction(e -> handleSave());
        removeButton.setOnAction(e -> handleRemove());
```
`setOnAction` means — when this button is clicked, call this method. `e` is the click event
object (we do not need it here so we ignore it).

---

```java
String genre = genreComboBox.getValue();
String name  = movieNameField.getText();
```
Reads the current values from the UI. `getValue()` gets the selected item from the dropdown,
`getText()` reads what the user typed in the text field.

---

```java
if (genre == null) {
        saveStatusLabel.setText("Please select a genre.");
    return;
            }
            if (name == null || name.isBlank()) {
        saveStatusLabel.setText("Movie name cannot be empty.");
    return;
            }
```
Input validation. If either field is missing, show an error in the status label and `return` —
stop the method right there, do not continue.

---

```java
boolean added = store.addMovie(genre, name);
if (added) {
        saveStatusLabel.setText("\"" + name.trim() + "\" saved to " + genre + ".");
        movieNameField.clear();
refreshRegistered(genre);
} else {
        saveStatusLabel.setText("Movie already exists in this genre.");
}
```
Calls `addMovie()` on the store. If it returned `true`, show a success message, clear the text
field, and refresh the registered dropdown so the new movie appears. If `false`, it was a
duplicate — show the error.

---

```java
List<String> names = store.getMovies(genre)
        .stream()
        .map(Movie::getName)
        .collect(Collectors.toList());
registeredComboBox.setItems(FXCollections.observableArrayList(names));
        registeredComboBox.getSelectionModel().clearSelection();
```
Gets movies for the genre from the store. `.stream()` converts the list so we can process it.
`.map(Movie::getName)` transforms each `Movie` object into just its name string.
`.collect(Collectors.toList())` gathers the results back into a plain list.
That list is pushed into the dropdown. `clearSelection()` resets any previously selected item.

---
---

### `MoviesApp.java`

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
```
`Application` is the base class for all JavaFX apps. `Scene` is the content inside the window.
`Stage` is the window itself.

---

```java
public class MoviesApp extends Application {
```
Every JavaFX app must extend `Application`. This gives it the JavaFX lifecycle methods like `start()`.

---

```java
@Override
public void start(Stage stage) {
```
JavaFX calls this automatically after `launch()`. The `Stage` is the window — JavaFX creates it
and passes it in for you.

---

```java
MoviesView view = new MoviesView();
Scene scene = new Scene(view, 350, 400);
```
Creates the UI. `new Scene(view, 350, 400)` wraps it in a scene with a width of 350px
and height of 400px.

---

```java
stage.setTitle("Movie Rental System - Movies");
stage.setScene(scene);
stage.setResizable(false);
stage.show();
```
`setTitle()` sets the window title bar text. `setScene()` puts the scene into the window.
`setResizable(false)` locks the window size. `show()` makes the window visible.

---

```java
public static void main(String[] args) {
    launch(args);
}
```
The entry point Java looks for when running the program. `launch()` starts the JavaFX engine
and eventually calls `start()`. You always just call `launch(args)` and let JavaFX take over.

---

## Notes
- Data is **in memory only** — resets when the app closes
- The Singleton in `GenreStore` means future modules (Customers, Rentals) can share the same
  data without passing objects around
- No FXML — the entire UI is built directly in Java
