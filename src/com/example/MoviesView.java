package com.example;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class MoviesView extends VBox {

    private final GenreStore store = GenreStore.getInstance();

    // UI controls
    private final ComboBox<String> genreComboBox      = new ComboBox<>();
    private final TextField        movieNameField      = new TextField();
    private final Button           saveButton          = new Button("Save Movie");
    private final Label            saveStatusLabel     = new Label();

    private final ComboBox<String> registeredComboBox  = new ComboBox<>();
    private final Button           removeButton         = new Button("Remove Movie");
    private final Label            removeStatusLabel    = new Label();

    public MoviesView() {
        buildUI();
        wireEvents();
    }

    private void buildUI() {
        setSpacing(10);
        setPadding(new Insets(20));

        // Genre dropdown — pre-loaded with all genres
        genreComboBox.setItems(FXCollections.observableArrayList(GenreStore.GENRES));
        genreComboBox.setPromptText("Select a genre");

        // Registered dropdown — shows movies in selected genre
        registeredComboBox.setPromptText("Select a movie");

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
    }

    private void wireEvents() {
        // When genre changes, refresh the registered list
        genreComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> refreshRegistered(newVal)
        );

        saveButton.setOnAction(e -> handleSave());
        removeButton.setOnAction(e -> handleRemove());
    }

    private void handleSave() {
        String genre = genreComboBox.getValue();
        String name  = movieNameField.getText();

        if (genre == null) {
            saveStatusLabel.setText("Please select a genre.");
            return;
        }
        if (name == null || name.isBlank()) {
            saveStatusLabel.setText("Movie name cannot be empty.");
            return;
        }

        boolean added = store.addMovie(genre, name);
        if (added) {
            saveStatusLabel.setText("\"" + name.trim() + "\" saved to " + genre + ".");
            movieNameField.clear();
            refreshRegistered(genre);
        } else {
            saveStatusLabel.setText("Movie already exists in this genre.");
        }
    }

    private void handleRemove() {
        String genre    = genreComboBox.getValue();
        String selected = registeredComboBox.getValue();

        if (genre == null) {
            removeStatusLabel.setText("Select a genre first.");
            return;
        }
        if (selected == null) {
            removeStatusLabel.setText("Select a movie to remove.");
            return;
        }

        boolean removed = store.removeMovie(genre, selected);
        if (removed) {
            removeStatusLabel.setText("\"" + selected + "\" removed.");
            refreshRegistered(genre);
        } else {
            removeStatusLabel.setText("Could not remove movie.");
        }
    }

    // Pulls movies for the selected genre and loads them into the registered dropdown
    private void refreshRegistered(String genre) {
        if (genre == null) {
            registeredComboBox.setItems(FXCollections.emptyObservableList());
            return;
        }
        List<String> names = store.getMovies(genre)
                .stream()
                .map(Movie::getName)
                .collect(Collectors.toList());
        registeredComboBox.setItems(FXCollections.observableArrayList(names));
        registeredComboBox.getSelectionModel().clearSelection();
    }
}